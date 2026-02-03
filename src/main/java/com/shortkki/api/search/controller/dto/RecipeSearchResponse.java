package com.shortkki.api.search.controller.dto;

import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Slice;

@Builder
public record RecipeSearchResponse(
        List<RecipeSummaryResponse> searchResult,
        SlicePageInfoResponse pageInfo
) {

    public static RecipeSearchResponse from(Slice<RecipeSearchItem> slice) {
        List<RecipeSummaryResponse> result = slice.getContent().stream()
                .map(item ->
                        new RecipeSummaryResponse(
                                item.id(),
                                item.title(),
                                item.bookmarkCount(),
                                item.sourceUrl(),
                                item.mainImgUrl(),
                                item.recipeSource(),
                                item.authorName(),
                                item.authorProfileImgUrl(),
                                item.platform(),
                                item.creatorName(),
                                item.creatorProfileImgUrl()
                        )
                ).toList();

        return RecipeSearchResponse.builder()
                .searchResult(result)
                .pageInfo(SlicePageInfoResponse.from(slice))
                .build();
    }
}
