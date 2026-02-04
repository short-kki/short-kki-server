package com.shortkki.api.curation.controller.dto.response;

import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record CurationRecommendResponse(
        long curationId,
        String title,
        String description,
        List<RecipeSummaryResponse> recipes,
        SlicePageInfoResponse pageInfo
) {
}
