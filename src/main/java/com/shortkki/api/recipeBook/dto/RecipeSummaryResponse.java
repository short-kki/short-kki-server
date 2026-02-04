package com.shortkki.api.recipeBook.dto;

import com.shortkki.api.recipe.entity.Recipe;

public record RecipeSummaryResponse(
        Long id,
        String title,
        Integer bookmarkCount,
        String mainImgUrl,
        String authorName
) {

    public static RecipeSummaryResponse from(Recipe recipe) {
        return new RecipeSummaryResponse(
                recipe.getId(),
                recipe.getBasicInfo().getTitle(),
                recipe.getBookmarkCount(),
                recipe.getMainImgUrl(),
                recipe.getAuthorName()
        );
    }
}
