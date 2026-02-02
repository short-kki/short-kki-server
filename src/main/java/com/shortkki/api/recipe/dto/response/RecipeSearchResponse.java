package com.shortkki.api.recipe.dto.response;

import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record RecipeSearchResponse(
    List<RecipeSummaryResponse> searchResult,
    SlicePageInfoResponse pageInfo
) {

}
