package com.shortkki.api.recipeBook.dto;

import com.shortkki.api.recipe.entity.Recipe;

public record RecipeSummaryResponse(
        Long id,
        String title,
        Integer cookingTime,
        Integer bookmarkCount,
        String mainImgUrl,
        String authorName,
        String authorProfileImgUrl
) {

    public static RecipeSummaryResponse from(Recipe recipe) {
        return new RecipeSummaryResponse(
                recipe.getId(),
                recipe.getBasicInfo().getTitle(),
                recipe.getBasicInfo().getCookingTime(),
                recipe.getBookmarkCount(),
                recipe.getMainImgUrl(),
                recipe.getAuthorName(),
                recipe.getAuthorProfileImgUrl()
        );
    }
}
