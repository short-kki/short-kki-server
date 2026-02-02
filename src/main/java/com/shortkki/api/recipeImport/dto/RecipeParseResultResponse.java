package com.shortkki.api.recipeImport.dto;

import java.util.List;

public record RecipeParseResultResponse(
        String title,
        String description,
        Integer servingSize,
        Integer cookingTime,
        String cuisineType,
        String mealType,
        String difficulty,
        List<IngredientDto> ingredients,
        List<StepDto> steps
) {

    public static RecipeParseResultResponse from(RecipeParseResult parseResult) {
        return new RecipeParseResultResponse(
                parseResult.title(),
                parseResult.description(),
                parseResult.servingSize(),
                parseResult.cookingTime(),
                parseResult.cuisineType(),
                parseResult.mealType(),
                parseResult.difficulty(),
                parseResult.ingredients().stream()
                        .map(ing -> new IngredientDto(
                                ing.name(),
                                ing.amount(),
                                ing.unit()))
                        .toList(),
                parseResult.steps().stream()
                        .map(step -> new StepDto(
                                step.stepNumber(),
                                step.description()))
                        .toList()
        );
    }

    public record IngredientDto(
            String name,
            String amount,
            String unit
    ) {
    }

    public record StepDto(
            int stepNumber,
            String description
    ) {
    }
}
