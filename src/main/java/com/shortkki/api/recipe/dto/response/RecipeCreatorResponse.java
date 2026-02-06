package com.shortkki.api.recipe.dto.response;

import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeCreatorResponse(
        SourcePlatform platform,
        String name,
        String profileImgUrl
) {

}
