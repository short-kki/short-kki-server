package com.shortkki.api.recipeBook.dto;

import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;

public record RecipeBookListResponse(
        List<RecipeBookResponse> recipeBooks,
        SlicePageInfoResponse pageInfo
) {
}
