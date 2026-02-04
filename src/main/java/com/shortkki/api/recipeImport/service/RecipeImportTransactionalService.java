package com.shortkki.api.recipeImport.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.entity.RecipeTag;
import com.shortkki.api.recipe.entity.TagSource;
import com.shortkki.api.recipe.entity.vo.RecipeBasicInfo;
import com.shortkki.api.recipe.entity.vo.RecipeCategoryInfo;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipe.repository.RecipeStepRepository;
import com.shortkki.api.recipe.repository.RecipeTagRepository;
import com.shortkki.api.recipe.service.IngredientQueryService;
import com.shortkki.api.recipe.service.TagService;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.IngredientParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.StepParseResult;
import com.shortkki.api.source.domain.SourceContent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeImportTransactionalService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;

    private final IngredientQueryService ingredientQueryService;

    private final TagService tagService;
    private final RecipeTagRepository recipeTagRepository;

    @Transactional
    public Recipe saveRecipeWithRelations(
            Member member,
            SourceContent sourceContent,
            RecipeParseResult parseResult,
            Set<String> originalParsedTags
    ) {
        Recipe recipe = saveRecipe(member, sourceContent, parseResult);

        saveIngredients(recipe, safeList(parseResult.ingredients()));
        saveSteps(recipe, safeList(parseResult.steps()));
        saveTags(recipe, safeList(parseResult.tags()), originalParsedTags);

        return recipe;
    }

    private Recipe saveRecipe(
            Member member, SourceContent sourceContent, RecipeParseResult parseResult
    ) {
        RecipeBasicInfo basicInfo = new RecipeBasicInfo(
                orDefault(parseResult.title(), sourceContent.getTitle()),
                orDefault(parseResult.description(), ""),
                orDefault(parseResult.servingSize(), 1),
                orDefault(parseResult.cookingTime(), 0)
        );

        RecipeCategoryInfo categoryInfo = new RecipeCategoryInfo(
                orDefault(parseResult.cuisineType(), CuisineType.ETC),
                orDefault(parseResult.mealType(), MealType.ETC),
                orDefault(parseResult.difficulty(), Difficulty.ETC)
        );

        return recipeRepository.save(
                Recipe.createImported(member, basicInfo, categoryInfo, sourceContent)
        );
    }

    private void saveIngredients(Recipe recipe, List<IngredientParseResult> ingredients) {
        List<RecipeIngredient> entities = ingredients.stream()
                .filter(ing -> ing.name() != null && !ing.name().isBlank())
                .map(ing -> toRecipeIngredient(recipe, ing))
                .toList();

        recipeIngredientRepository.saveAll(entities);
    }

    private RecipeIngredient toRecipeIngredient(Recipe recipe, IngredientParseResult ing) {
        String rawName = normalizeText(ing.name());
        Ingredient ingredient = ingredientQueryService.findStandardByNameOrNull(rawName);
        String name = (ingredient != null) ? ingredient.getName() : rawName;

        Double amount = ing.amount();
        String unit = normalizeUnit(ing.unit());

        return RecipeIngredient.create(ingredient, recipe, name, amount, unit);
    }

    private void saveSteps(Recipe recipe, List<StepParseResult> steps) {
        for (StepParseResult step : steps) {
            recipeStepRepository.save(
                    RecipeStep.create(recipe, step.stepNumber(), step.description())
            );
        }
    }

    private void saveTags(Recipe recipe, List<String> tags, Set<String> originalParsedTags) {
        List<String> normalized = normalizeTags(tags);
        if (normalized.isEmpty()) {
            return;
        }

        Set<String> original = (originalParsedTags != null) ? originalParsedTags : Set.of();

        List<RecipeTag> recipeTags = new ArrayList<>(normalized.size());
        for (String name : normalized) {
            TagSource source = original.contains(name) ? TagSource.SYSTEM : TagSource.USER;
            recipeTags.add(RecipeTag.of(recipe.getId(), tagService.getOrCreateTag(name, source).getId()));
        }

        recipeTagRepository.saveAll(recipeTags);
    }

    private List<String> normalizeTags(List<String> tags) {
        return safeList(tags).stream()
                .filter(t -> t != null && !t.isBlank())
                .map(String::trim)
                .distinct()
                .toList();
    }

    private String normalizeText(String s) {
        return (s == null) ? "" : s.trim();
    }

    private String normalizeUnit(String unit) {
        return (unit != null && !unit.isBlank()) ? unit.trim() : "단위없음";
    }

    private <T> List<T> safeList(List<T> list) {
        return (list == null) ? List.of() : list;
    }

    private <T> T orDefault(T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }
}
