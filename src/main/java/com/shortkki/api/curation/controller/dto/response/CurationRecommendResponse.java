package com.shortkki.api.curation.controller.dto.response;

import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
public record CurationRecommendResponse(
        long curationId,
        String title,
        String description,
        Set<MealType> mealTypes,
        Set<CuisineType> cuisineTypes,
        List<RecipeSummaryResponse> recipes,
        SlicePageInfoResponse pageInfo
) {
}
