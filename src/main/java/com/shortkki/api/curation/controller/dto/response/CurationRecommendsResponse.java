package com.shortkki.api.curation.controller.dto.response;

import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;

public record CurationRecommendsResponse(
        List<CurationRecommendResponse> curations,
        SlicePageInfoResponse pageInfo
) {

}
