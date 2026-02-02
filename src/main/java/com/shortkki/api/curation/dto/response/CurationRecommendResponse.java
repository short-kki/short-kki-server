package com.shortkki.api.curation.dto.response;

import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record CurationRecommendResponse(
        Long curationId,
        String title,
        String description,
        List<RecipeSummaryResponse> recipes
) {
}
