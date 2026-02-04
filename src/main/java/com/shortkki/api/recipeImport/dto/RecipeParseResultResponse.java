package com.shortkki.api.recipeImport.dto;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import java.util.List;

public record RecipeParseResultResponse(
        String title,
        String description,
        Integer servingSize,
        Integer cookingTime,
        CuisineType cuisineType,
        MealType mealType,
        Difficulty difficulty,
        List<IngredientDto> ingredients,
        List<StepDto> steps,
        List<String> tags
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
                        .toList(),

                parseResult.tags() != null ? parseResult.tags() : List.of()
        );
    }

    public record IngredientDto(
            String name,
            Double amount,
            String unit
    ) {

    }

    public record StepDto(
            int stepNumber,
            String description
    ) {

    }
}
