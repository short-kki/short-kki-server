package com.shortkki.api.recipe.dto.response;

import lombok.Builder;

@Builder
public record RecipeSummaryResponse(
        Long id,
        String title,
        Integer bookmarkCount,
        String thumbnailUrl,
        String authorName
) {

}
