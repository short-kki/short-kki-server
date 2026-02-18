package com.shortkki.api.recipeBook.dto;

import com.shortkki.api.recipeBook.entity.RecipeBook;
import java.time.LocalDateTime;
import java.util.List;

public record RecipeBookResponse(
                Long id,
                String title,
                Boolean isDefault,
                Integer sortOrder,
                LocalDateTime createdAt,
                Long recipeCount,
                List<RecipeSummaryResponse> recipes) {

        public static RecipeBookResponse from(RecipeBook recipeBook) {
                return new RecipeBookResponse(
                                recipeBook.getId(),
                                recipeBook.getTitle(),
                                recipeBook.getIsDefault(),
                                recipeBook.getSortOrder(),
                                recipeBook.getCreatedAt(),
                                0L,
                                List.of());
        }

        public static RecipeBookResponse from(RecipeBook recipeBook,
                        List<RecipeSummaryResponse> recipes,
                        long recipeCount) {
                return new RecipeBookResponse(
                                recipeBook.getId(),
                                recipeBook.getTitle(),
                                recipeBook.getIsDefault(),
                                recipeBook.getSortOrder(),
                                recipeBook.getCreatedAt(),
                                recipeCount,
                                recipes);
        }
}
