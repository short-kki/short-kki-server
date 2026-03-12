package com.shortkki.api.search.application.port.dto;

import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeSearchItem(
        Long id,
        String title,
        int bookmarkCount,
        int cookingTime,
        String mainImgUrl,
        RecipeSource recipeSource,
        String authorName,
        String authorProfileImgUrl,
        SourcePlatform platform,
        String sourceUrl,
        String creatorName,
        String creatorProfileImgUrl
) {

}
