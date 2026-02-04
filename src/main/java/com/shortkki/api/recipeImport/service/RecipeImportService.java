package com.shortkki.api.recipeImport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeImport.dto.RecipeEditRequest;
import com.shortkki.api.recipeImport.dto.RecipeImportPreviewResponse;
import com.shortkki.api.recipeImport.dto.RecipeImportRequest;
import com.shortkki.api.recipeImport.dto.RecipeImportResponse;
import com.shortkki.api.recipeImport.dto.RecipeImportStatusResponse;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResultResponse;
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
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class RecipeImportService {

    private final RecipeRepository recipeRepository;
    private final SourceContentService sourceContentService;
    private final SourceContentRepository sourceContentRepository;
    private final ExternalKeyExtractorRegistry extractorRegistry;
    private final MemberRepository memberRepository;
    private final SourceImportHistoryRepository sourceImportHistoryRepository;

    private final RecipeImportAsyncService recipeImportAsyncService;
    private final RecipeImportTransactionalService transactionalService;
    private final RecipeBookService recipeBookService;
    private final RecipeBookQueryService recipeBookQueryService;

    private final ObjectMapper objectMapper;

    @Transactional
    public RecipeImportResponse importFromUrl(Long memberId, RecipeImportRequest request) {
        Member member = findMemberById(memberId);
        String sourceUrl = request.sourceUrl();

        validateNotDuplicateSource(sourceUrl);

        SourceContent sourceContent = sourceContentService.resolveSourceContent(sourceUrl);
        SourceImportHistory history = createHistory(member, sourceContent, sourceUrl);
        RecipeImportPreviewResponse preview = loadPreview(sourceContent.getId());

        Long resolvedMemberId = member.getId();
        Long resolvedSourceContentId = sourceContent.getId();
        Long resolvedHistoryId = history.getId();
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        recipeImportAsyncService.processImport(
                                resolvedMemberId, resolvedSourceContentId, resolvedHistoryId, sourceUrl
                        );
                    }
                });

        return RecipeImportResponse.accepted(history.getId(), sourceUrl, preview);
    }

    public RecipeImportStatusResponse getStatus(Long memberId, Long historyId) {
        SourceImportHistory history = findHistory(historyId);
        validateHistoryOwnership(history, memberId);

        RecipeImportPreviewResponse preview = loadPreview(history.getSourceContentId());
        return RecipeImportStatusResponse.from(history, preview);
    }

    public RecipeParseResultResponse getParsedRecipe(Long memberId, Long historyId) {
        SourceImportHistory history = findHistory(historyId);
        validateHistoryOwnership(history, memberId);
        validateStatusParsed(history);
        validateParsedContentExists(history);

        RecipeParseResult parseResult = readParseResult(history.getParsedContent());
        return RecipeParseResultResponse.from(parseResult);
    }

    @Transactional
    public Long confirmAndSave(Long memberId, Long historyId, RecipeEditRequest request) {
        SourceImportHistory history = findHistory(historyId);
        validateHistoryOwnership(history, memberId);
        validateStatusParsed(history);

        Member member = findMemberById(memberId);
        SourceContent sourceContent = findSourceContent(history.getSourceContentId());

        Set<String> originalParsedTags = extractOriginalTags(history);
        RecipeParseResult parseResult = convertToParseResult(request);
        Recipe savedRecipe = saveRecipe(member, sourceContent, parseResult, originalParsedTags);

        completeHistory(history, savedRecipe.getId());
        addToDefaultRecipeBook(memberId, savedRecipe.getId());

        return savedRecipe.getId();
    }

    // -------------------------
    // Helper methods (query)
    // -------------------------

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private SourceImportHistory findHistory(Long historyId) {
        return sourceImportHistoryRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
    }

    private SourceContent findSourceContent(Long sourceContentId) {
        return sourceContentRepository.findById(sourceContentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
    }

    private RecipeImportPreviewResponse loadPreview(Long sourceContentId) {
        SourceContent previewContent = sourceContentRepository.findByIdWithCreator(sourceContentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
        return RecipeImportPreviewResponse.from(previewContent);
    }

    // -------------------------
    // Helper methods (validation)
    // -------------------------

    private void validateNotDuplicateSource(String sourceUrl) {
        SourcePlatform platform = extractorRegistry.detectPlatform(sourceUrl)
                .orElseThrow(() -> new BadRequestException(ErrorCode.UNSUPPORTED_SOURCE_PLATFORM));
        String externalKey = extractorRegistry.extractKey(platform, sourceUrl);

        boolean isDuplicate = sourceContentRepository.findByPlatformAndExternalKey(platform, externalKey)
                .map(sc -> recipeRepository.existsBySourceContentId(sc.getId()))
                .orElse(false);

        if (isDuplicate) {
            throw new BusinessException(ErrorCode.SOURCE_CONTENT_ALREADY_EXISTS);
        }
    }

    private void validateHistoryOwnership(SourceImportHistory history, Long memberId) {
        if (history.getMemberId() == null || !history.getMemberId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void validateStatusParsed(SourceImportHistory history) {
        if (history.getStatus() != ImportStatus.PARSED) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private void validateParsedContentExists(SourceImportHistory history) {
        if (history.getParsedContent() == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    private Set<String> extractOriginalTags(SourceImportHistory history) {
        String parsedContent = history.getParsedContent();
        if (parsedContent == null || parsedContent.isBlank()) {
            return Set.of();
        }

        try {
            RecipeParseResult parsed = objectMapper.readValue(parsedContent,
                    RecipeParseResult.class);
            List<String> tags = parsed.tags();
            if (tags == null || tags.isEmpty()) {
                return Set.of();
            }

            return tags.stream()
                    .filter(t -> t != null && !t.isBlank())
                    .map(String::trim)
                    .collect(java.util.stream.Collectors.toSet());
        } catch (Exception e) {
            return Set.of();
        }
    }

    // -------------------------
    // Helper methods (commands)
    // -------------------------

    private SourceImportHistory createHistory(Member member, SourceContent sourceContent,
            String sourceUrl) {
        SourceImportHistory history = SourceImportHistory.create(
                member.getId(),
                sourceContent.getId(),
                sourceUrl,
                sourceContent.getPlatform()
        );
        return sourceImportHistoryRepository.save(history);
    }

    private RecipeParseResult readParseResult(String json) {
        try {
            return objectMapper.readValue(json, RecipeParseResult.class);
        } catch (Exception e) {
            throw new BusinessException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "파싱된 레시피 데이터를 읽는 중 오류가 발생했습니다."
            );
        }
    }

    private Recipe saveRecipe(Member member, SourceContent sourceContent,
            RecipeParseResult parseResult,
            Set<String> originalParsedTags
    ) {
        try {
            return transactionalService.saveRecipeWithRelations(member, sourceContent, parseResult,
                    originalParsedTags);
        } catch (Exception e) {
            throw new BusinessException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "레시피 저장 중 오류가 발생했습니다: " + e.getMessage()
            );
        }
    }

    private void completeHistory(SourceImportHistory history, Long recipeId) {
        history.complete(recipeId);
        sourceImportHistoryRepository.save(history);
    }

    private void addToDefaultRecipeBook(Long memberId, Long recipeId) {
        recipeBookQueryService.findDefaultByMemberId(memberId)
                .ifPresent(defaultBook ->
                        recipeBookService.addRecipeIfNotExists(memberId, defaultBook.getId(),
                                recipeId)
                );
    }

    // -------------------------
    // Conversion
    // -------------------------

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
