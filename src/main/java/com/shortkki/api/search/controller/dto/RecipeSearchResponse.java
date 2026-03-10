package com.shortkki.api.search.controller.dto;

import com.shortkki.api.recipe.dto.response.RecipeSearchItemResponse;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record RecipeSearchResponse(
        List<RecipeSearchItemResponse> searchResult,
        SlicePageInfoResponse pageInfo
) {
}
