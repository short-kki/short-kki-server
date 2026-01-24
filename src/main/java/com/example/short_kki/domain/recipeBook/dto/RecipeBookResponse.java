package com.example.short_kki.domain.recipeBook.dto;

import com.example.short_kki.domain.recipeBook.entity.RecipeBook;
import java.time.LocalDateTime;

public record RecipeBookResponse(
        Long id,
        String title,
        Boolean isDefault,
        Integer sortOrder,
        LocalDateTime createdAt
) {

    public static RecipeBookResponse from(RecipeBook recipeBook) {
        return new RecipeBookResponse(
                recipeBook.getId(),
                recipeBook.getTitle(),
                recipeBook.getIsDefault(),
                recipeBook.getSortOrder(),
                recipeBook.getCreatedAt());
    }
}
