package com.shortkki.api.recipeImport.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipe.constant.CuisineType;
import com.shortkki.api.recipe.constant.Difficulty;
import com.shortkki.api.recipe.constant.MealType;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipe.repository.RecipeStepRepository;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.service.RecipeBookQueryService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.api.recipeImport.dto.RecipeImportRequest;
import com.shortkki.api.recipeImport.dto.RecipeImportResponse;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.IngredientParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.StepParseResult;
import com.shortkki.api.recipeImport.service.parser.AiRecipeParserService;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceImportHistory;
import com.shortkki.api.source.repository.SourceImportHistoryRepository;
import com.shortkki.api.source.service.SourceContentService;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecipeImportService {

    private final SourceContentService sourceContentService;
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final RecipeBookService recipeBookService;
    private final RecipeBookQueryService recipeBookQueryService;
    private final SourceImportHistoryRepository sourceImportHistoryRepository;
    private final AiRecipeParserService aiRecipeParserService;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;

    public RecipeImportResponse importFromUrl(Long memberId, RecipeImportRequest request) {
        Member member = findMemberById(memberId);
        String sourceUrl = request.sourceUrl();

        SourceContent sourceContent = sourceContentService.resolveSourceContent(sourceUrl);

        SourceImportHistory history = SourceImportHistory.create(
                sourceContent.getId(),
                sourceUrl,
                sourceContent.getPlatform());
        sourceImportHistoryRepository.save(history);

        log.info("AI 레시피 파싱 시작: {}", sourceUrl);
        RecipeParseResult parseResult = aiRecipeParserService.parseRecipeFromUrl(sourceUrl);
        log.info("AI 파싱 완료 - 제목: {}, 재료: {}개, 순서: {}개",
                parseResult.title(),
                parseResult.ingredients().size(),
                parseResult.steps().size());

        Recipe recipe = Recipe.createImported(
                member,
                parseResult.title() != null ? parseResult.title() : sourceContent.getTitle(),
                parseResult.description(),
                parseResult.servingSize(),
                parseResult.cookingTime(),
                parseCuisineType(parseResult.cuisineType()),
                parseMealType(parseResult.mealType()),
                parseDifficulty(parseResult.difficulty()),
                sourceContent);
        Recipe saved = recipeRepository.save(recipe);

        saveIngredients(saved, parseResult.ingredients());

        saveSteps(saved, parseResult.steps());

        history.complete("Recipe created: " + saved.getId());

        addToDefaultRecipeBook(memberId, saved.getId());

        return RecipeImportResponse.success(saved.getId(), saved.getTitle(), sourceUrl);
    }

    private void saveIngredients(Recipe recipe, List<IngredientParseResult> ingredients) {
        for (IngredientParseResult ing : ingredients) {
            Ingredient ingredient = ingredientRepository.findByName(ing.name())
                    .orElseGet(() -> ingredientRepository.save(
                            Ingredient.create(ing.name(), ing.amount())));
            Integer amount = parseAmount(ing.amount());
            RecipeIngredient recipeIngredient = RecipeIngredient.create(
                    ingredient, recipe, amount);
            recipeIngredientRepository.save(recipeIngredient);
        }
    }

    private Integer parseAmount(String amountStr) {
        if (amountStr == null || amountStr.isBlank()) {
            return null;
        }
        try {
            String numberOnly = amountStr.replaceAll("[^0-9]", "");
            return numberOnly.isEmpty() ? null : Integer.parseInt(numberOnly);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void saveSteps(Recipe recipe, List<StepParseResult> steps) {
        for (StepParseResult step : steps) {
            RecipeStep recipeStep = RecipeStep.create(
                    recipe, step.stepNumber(), step.description());
            recipeStepRepository.save(recipeStep);
        }
    }

    private void addToDefaultRecipeBook(Long memberId, Long recipeId) {
        recipeBookQueryService.findAllByMemberId(memberId).stream()
                .filter(RecipeBook::getIsDefault)
                .findFirst()
                .ifPresent(defaultBook -> recipeBookService.addRecipeInternal(defaultBook.getId(),
                        recipeId));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private CuisineType parseCuisineType(String value) {
        if (value == null)
            return null;
        try {
            return CuisineType.valueOf(value);
        } catch (IllegalArgumentException e) {
            log.warn("Unknown cuisineType: {}", value);
            return null;
        }
    }

    private MealType parseMealType(String value) {
        if (value == null)
            return null;
        try {
            return MealType.valueOf(value);
        } catch (IllegalArgumentException e) {
            log.warn("Unknown mealType: {}", value);
            return null;
        }
    }

    private Difficulty parseDifficulty(String value) {
        if (value == null)
            return null;
        try {
            return Difficulty.valueOf(value);
        } catch (IllegalArgumentException e) {
            log.warn("Unknown difficulty: {}", value);
            return null;
        }
    }
}
