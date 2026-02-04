package com.shortkki.api.recipeImport.dto;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import java.util.List;

public record RecipeParseResult(
        String title,
        String description,
        Integer servingSize,
        Integer cookingTime,
        CuisineType cuisineType,
        MealType mealType,
        Difficulty difficulty,
        List<IngredientParseResult> ingredients,
        List<StepParseResult> steps,
        List<String> tags,
        String rawResponse
) {

    public static RecipeParseResult empty(String title) {
        return new RecipeParseResult(
                title,
                "",
                1,
                0,
                CuisineType.ETC,
                MealType.ETC,
                Difficulty.ETC,
                List.of(),
                List.of(),
                List.of(),
                null);
    }

    public record IngredientParseResult(
            String name,
            Double amount,
            String unit
    ) {

    }

    public record StepParseResult(
            int stepNumber,
            String description
    ) {

    }
}
