package com.shortkki.api.recipeBook.dto;

import com.shortkki.global.response.page.SlicePageInfoResponse;

public record RecipeBookDetailResponse(
        RecipeBookResponse recipeBook,
        SlicePageInfoResponse pageInfo
) {
}
