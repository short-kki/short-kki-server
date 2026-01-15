package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.constant.CuisineType;
import com.example.short_kki.domain.recipe.constant.Difficulty;
import com.example.short_kki.domain.recipe.constant.MealType;
import com.example.short_kki.domain.recipe.constant.RecipeStatus;
import com.example.short_kki.domain.recipe.constant.SourceContentType;
import com.example.short_kki.domain.recipe.constant.SourcePlatform;
import com.example.short_kki.domain.recipe.constant.SourceType;
import com.example.short_kki.domain.recipe.entity.Recipe;
import java.time.LocalDateTime;

/**
 * 레시피 응답 DTO
 */
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
        RecipeStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    
    public static RecipeResponse from(Recipe recipe) {
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
                recipe.getStatus(),
                recipe.getCreatedAt(),
                recipe.getUpdatedAt());
    }
}
