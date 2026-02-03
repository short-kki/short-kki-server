package com.shortkki.api.recipe.dto.response;

import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeSummaryResponse(
        Long id,
        String title,
        int bookmarkCount,
        String sourceUrl,
        String mainImgUrl,
        RecipeSource recipeSource,
        String authorName,
        String authorProfileImgUrl,
        SourcePlatform platform,
        String creatorName,
        String creatorProfileImgUrl
) {

}
