package com.shortkki.api.recipeImport.dto;

import java.util.List;

public record RecipeParseResult(
        String title,
        String description,
        Integer servingSize,
        Integer cookingTime,
        String cuisineType,
        String mealType,
        String difficulty,
        List<IngredientParseResult> ingredients,
        List<StepParseResult> steps,
        String rawResponse
) {

    public static RecipeParseResult empty(String title) {
        return new RecipeParseResult(title, null, 1, 30, null, null, null, List.of(), List.of(), null);
    }

    public record IngredientParseResult(
            String name,
            String amount,
            String unit
    ) {

    }

    public record StepParseResult(
            int stepNumber,
            String description
    ) {

    }
}
