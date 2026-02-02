package com.shortkki.api.recipe.dto.response;

import com.shortkki.api.recipe.entity.Recipe;

public record RecipeSummaryResponse(
        Long id,
        String title,
        Integer bookmarkCount,
        String thumbnailUrl,
        String authorName
) {

    public static RecipeSummaryResponse from(Recipe recipe) {
        return new RecipeSummaryResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getBookmarkCount(),
                null,
                null
        );
    }
}
