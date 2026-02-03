package com.shortkki.api.recipeImport.service;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipeImport.dto.RecipeEditRequest;
import com.shortkki.api.recipeImport.dto.RecipeImportRequest;
import com.shortkki.api.recipeImport.dto.RecipeImportPreview;
import com.shortkki.api.recipeImport.dto.RecipeImportResponse;
import com.shortkki.api.recipeImport.dto.RecipeImportStatusResponse;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResultResponse;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipeBook.service.RecipeBookQueryService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.api.source.domain.ImportStatus;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceImportHistory;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.source.repository.SourceContentRepository;
import com.shortkki.api.source.repository.SourceImportHistoryRepository;
import com.shortkki.api.source.service.SourceContentService;
import com.shortkki.api.source.support.ExternalKeyExtractorRegistry;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeImportService {

    private final SourceContentService sourceContentService;
    private final SourceContentRepository sourceContentRepository;
    private final ExternalKeyExtractorRegistry extractorRegistry;
    private final MemberRepository memberRepository;
    private final SourceImportHistoryRepository sourceImportHistoryRepository;
    private final RecipeImportAsyncService recipeImportAsyncService;
    private final RecipeImportTransactionalService transactionalService;
    private final RecipeBookService recipeBookService;
    private final RecipeBookQueryService recipeBookQueryService;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Transactional
    public RecipeImportResponse importFromUrl(Long memberId, RecipeImportRequest request) {
        Member member = findMemberById(memberId);
        String sourceUrl = request.sourceUrl();

        validateNotDuplicateSource(sourceUrl);

        SourceContent sourceContent = sourceContentService.resolveSourceContent(sourceUrl);

        SourceImportHistory history = SourceImportHistory.create(
                member.getId(),
                sourceContent.getId(),
                sourceUrl,
                sourceContent.getPlatform());
        sourceImportHistoryRepository.save(history);

        SourceContent previewContent = sourceContentRepository.findByIdWithCreator(
                        sourceContent.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
        RecipeImportPreview preview = RecipeImportPreview.from(previewContent);

        recipeImportAsyncService.processImport(
                member.getId(),
                sourceContent.getId(),
                history.getId(),
                sourceUrl);

        return RecipeImportResponse.accepted(history.getId(), sourceUrl, preview);
    }

    public RecipeImportStatusResponse getStatus(Long memberId, Long historyId) {
        SourceImportHistory history = sourceImportHistoryRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));

        validateHistoryOwnership(history, memberId);

        SourceContent previewContent = sourceContentRepository.findByIdWithCreator(
                        history.getSourceContentId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
        RecipeImportPreview preview = RecipeImportPreview.from(previewContent);

        return RecipeImportStatusResponse.from(history, preview);
    }

    public RecipeParseResultResponse getParsedRecipe(Long memberId, Long historyId) {
        SourceImportHistory history = sourceImportHistoryRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));

        validateHistoryOwnership(history, memberId);

        if (history.getStatus() != ImportStatus.PARSED) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (history.getParsedContent() == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_ERROR);
        }

        try {
            RecipeParseResult parseResult = objectMapper.readValue(
                    history.getParsedContent(),
                    RecipeParseResult.class);
            return RecipeParseResultResponse.from(parseResult);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,
                    "파싱된 레시피 데이터를 읽는 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    public Long confirmAndSave(Long memberId, Long historyId, RecipeEditRequest request) {
        SourceImportHistory history = sourceImportHistoryRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));

        validateHistoryOwnership(history, memberId);

        if (history.getStatus() != ImportStatus.PARSED) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }

        Member member = findMemberById(memberId);
        SourceContent sourceContent = sourceContentRepository.findById(history.getSourceContentId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));

        RecipeParseResult parseResult = convertToParseResult(request);

        Recipe savedRecipe;
        try {
            savedRecipe = transactionalService.saveRecipeWithRelations(member, sourceContent,
                    parseResult);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,
                    "레시피 저장 중 오류가 발생했습니다: " + e.getMessage());
        }

        // history.updateRecipeId(savedRecipe.getId());
        history.complete(savedRecipe.getId());
        sourceImportHistoryRepository.save(history);

        recipeBookQueryService.findDefaultByMemberId(memberId)
                .ifPresent(defaultBook -> recipeBookService.addRecipeIfNotExists(
                        memberId, defaultBook.getId(), savedRecipe.getId()));

        return savedRecipe.getId();
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateNotDuplicateSource(String sourceUrl) {
        SourcePlatform platform = extractorRegistry.detectPlatform(sourceUrl)
                .orElseThrow(() -> new BadRequestException(ErrorCode.UNSUPPORTED_SOURCE_PLATFORM));
        String externalKey = extractorRegistry.extractKey(platform, sourceUrl);

        if (sourceContentRepository.findByPlatformAndExternalKey(platform, externalKey)
                .isPresent()) {
            throw new BusinessException(ErrorCode.SOURCE_CONTENT_ALREADY_EXISTS);
        }
    }

    private void validateHistoryOwnership(SourceImportHistory history, Long memberId) {
        if (history.getMemberId() == null || !history.getMemberId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    private RecipeParseResult convertToParseResult(RecipeEditRequest request) {
        var ingredients = request.ingredients().stream()
                .map(ing -> new RecipeParseResult.IngredientParseResult(
                        ing.name(),
                        ing.amount(),
                        ing.unit()))
                .toList();

        var steps = request.steps().stream()
                .map(step -> new RecipeParseResult.StepParseResult(
                        step.stepNumber(),
                        step.description()))
                .toList();

        return new RecipeParseResult(
                request.title(),
                request.description(),
                request.servingSize(),
                request.cookingTime(),
                parseEnum(request.cuisineType(), CuisineType.ETC, CuisineType.class),
                parseEnum(request.mealType(), MealType.ETC, MealType.class),
                parseEnum(request.difficulty(), Difficulty.ETC, Difficulty.class),
                ingredients,
                steps,
                request.tags() != null ? request.tags() : List.of(),
                null
        );
    }

    private <E extends Enum<E>> E parseEnum(String value, E fallback, Class<E> enumType) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        try {
            return Enum.valueOf(enumType, value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }
}
