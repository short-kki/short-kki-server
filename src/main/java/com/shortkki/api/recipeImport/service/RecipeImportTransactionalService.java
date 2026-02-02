package com.shortkki.api.recipeImport.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.recipe.constant.CuisineType;
import com.shortkki.api.recipe.constant.Difficulty;
import com.shortkki.api.recipe.constant.MealType;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.entity.vo.RecipeBasicInfo;
import com.shortkki.api.recipe.entity.vo.RecipeCategoryInfo;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipe.repository.RecipeStepRepository;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.IngredientParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.StepParseResult;
import com.shortkki.api.source.domain.SourceContent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeImportTransactionalService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;

    @Transactional
    public Recipe saveRecipeWithRelations(
            Member member,
            SourceContent sourceContent,
            RecipeParseResult parseResult) {
        Recipe recipe = saveRecipe(member, sourceContent, parseResult);
        saveIngredients(recipe, parseResult.ingredients());
        saveSteps(recipe, parseResult.steps());
        return recipe;
    }

    private Recipe saveRecipe(Member member, SourceContent sourceContent, RecipeParseResult parseResult) {
        RecipeBasicInfo basicInfo = new RecipeBasicInfo(
                parseResult.title() != null ? parseResult.title() : sourceContent.getTitle(),
                parseResult.description(),
                parseResult.servingSize() != null ? parseResult.servingSize() : 1,
                parseResult.cookingTime() != null ? parseResult.cookingTime() : 0);

        CuisineType cuisineType = parseCuisineType(parseResult.cuisineType());
        MealType mealType = parseMealType(parseResult.mealType());
        Difficulty difficulty = parseDifficulty(parseResult.difficulty());

        RecipeCategoryInfo categoryInfo = new RecipeCategoryInfo(
                cuisineType != null ? cuisineType : CuisineType.ETC,
                mealType != null ? mealType : MealType.ETC,
                difficulty != null ? difficulty : Difficulty.ETC);

        Recipe recipe = Recipe.createImported(
                member,
                basicInfo,
                categoryInfo,
                sourceContent);
        return recipeRepository.save(recipe);
    }

    private void saveIngredients(Recipe recipe, List<IngredientParseResult> ingredients) {
        for (IngredientParseResult ing : ingredients) {
            Ingredient ingredient = ingredientRepository.findByName(ing.name())
                    .orElseGet(() -> ingredientRepository.save(
                            Ingredient.create(ing.name())));
            Double amount = parseAmount(ing.amount());
            String unit = ing.unit() != null ? ing.unit() : "";
            RecipeIngredient recipeIngredient = RecipeIngredient.create(
                    ingredient, recipe, amount, unit);
            recipeIngredientRepository.save(recipeIngredient);
        }
    }

    private void saveSteps(Recipe recipe, List<StepParseResult> steps) {
        for (StepParseResult step : steps) {
            RecipeStep recipeStep = RecipeStep.create(
                    recipe, step.stepNumber(), step.description());
            recipeStepRepository.save(recipeStep);
        }
    }

    private Double parseAmount(String amountStr) {
        if (amountStr == null || amountStr.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private CuisineType parseCuisineType(String value) {
        if (value == null) {
            return null;
        }
        try {
            return CuisineType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private MealType parseMealType(String value) {
        if (value == null) {
            return null;
        }
        try {
            return MealType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Difficulty parseDifficulty(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Difficulty.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
