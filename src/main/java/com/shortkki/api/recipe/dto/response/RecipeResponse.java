package com.shortkki.api.recipe.dto.response;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.recipe.entity.SourceType;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
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
