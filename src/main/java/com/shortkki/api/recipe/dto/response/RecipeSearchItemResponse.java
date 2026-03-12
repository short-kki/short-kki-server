package com.shortkki.api.recipe.dto.response;

import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeSearchItemResponse(
        Long id,
        String title,
        int bookmarkCount,
        int cookingTime,
        String sourceUrl,
        String mainImgUrl,
        RecipeSource recipeSource,
        String authorName,
        String authorProfileImgUrl,
        SourcePlatform platform,
        String creatorName,
        String creatorProfileImgUrl,
        boolean isBookmarked
) {

    public static RecipeSearchItemResponse from(RecipeSearchItem item, boolean isBookmarked) {
        return new RecipeSearchItemResponse(
                item.id(),
                item.title(),
                item.bookmarkCount(),
                item.cookingTime(),
                item.sourceUrl(),
                item.mainImgUrl(),
                item.recipeSource(),
                item.authorName(),
                item.authorProfileImgUrl(),
                item.platform(),
                item.creatorName(),
                item.creatorProfileImgUrl(),
                isBookmarked
        );
    }
}
