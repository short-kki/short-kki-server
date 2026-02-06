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
        List<RecipeSummaryResponse> recipes
) {

    public static RecipeBookResponse from(RecipeBook recipeBook) {
        return new RecipeBookResponse(
                recipeBook.getId(),
                recipeBook.getTitle(),
                recipeBook.getIsDefault(),
                recipeBook.getSortOrder(),
                recipeBook.getCreatedAt(),
                List.of());
    }

    public static RecipeBookResponse from(RecipeBook recipeBook,
            List<RecipeSummaryResponse> recipes) {
        return new RecipeBookResponse(
                recipeBook.getId(),
                recipeBook.getTitle(),
                recipeBook.getIsDefault(),
                recipeBook.getSortOrder(),
                recipeBook.getCreatedAt(),
                recipes);
    }
}
