package com.shortkki.api.curation.controller.dto.response;

import com.shortkki.api.recipe.dto.response.RecipeSearchItemResponse;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;

public record RecipeCurationSearchResponse(
        Long curationId,
        List<RecipeSearchItemResponse> recipes,
        SlicePageInfoResponse pageInfo
) {

}
