package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.constant.CuisineType;
import com.example.short_kki.domain.recipe.constant.Difficulty;
import com.example.short_kki.domain.recipe.constant.MealType;
import com.example.short_kki.domain.recipe.constant.SourceContentType;
import com.example.short_kki.domain.recipe.constant.SourcePlatform;
import com.example.short_kki.domain.recipe.constant.SourceType;
import com.example.short_kki.domain.recipe.entity.Recipe;
import com.example.short_kki.domain.recipe.entity.RecipeIngredient;
import com.example.short_kki.domain.recipe.entity.RecipeStep;
import java.time.LocalDateTime;
import java.util.List;

public record RecipeResponse(
        Long id,
        String title,
        String description,
        Integer servingSize,
        Integer cookingTime,
        Integer bookmarkCount,
        CuisineType cuisineType,
        MealType mealType,
        Difficulty difficulty,
        SourceType sourceType,
        String sourceUrl,
        SourcePlatform sourcePlatform,
        SourceContentType sourceContentType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<StepResponse> steps,
        List<IngredientResponse> ingredients
) {

    public static RecipeResponse toDto(Recipe recipe, List<RecipeStep> steps,
            List<RecipeIngredient> ingredients) {

        List<StepResponse> stepResponses = steps.stream()
                .map(s -> new StepResponse(s.getStepOrder(), s.getDescription()))
                .toList();

        List<IngredientResponse> ingredientResponses = ingredients.stream()
                .map(i -> new IngredientResponse(
                        i.getIngredient().getName(),
                        i.getIngredient().getUnit(),
                        i.getAmount()))
                .toList();

        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getServingSize(),
                recipe.getCookingTime(),
                recipe.getBookmarkCount(),
                recipe.getCuisineType(),
                recipe.getMealType(),
                recipe.getDifficulty(),
                recipe.getSourceType(),
                recipe.getSourceUrl(),
                recipe.getSourcePlatform(),
                recipe.getSourceContentType(),
                recipe.getCreatedAt(),
                recipe.getUpdatedAt(),
                stepResponses,
                ingredientResponses);
    }
}
