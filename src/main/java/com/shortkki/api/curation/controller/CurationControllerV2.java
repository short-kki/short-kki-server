package com.shortkki.api.curation.controller;

import com.shortkki.api.curation.controller.dto.response.CurationRecommendsResponse;
import com.shortkki.api.curation.controller.dto.response.RecipeCurationSearchResponse;
import com.shortkki.api.curation.service.CurationQueryService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class CurationControllerV2 {

    private final CurationQueryService curationQueryService;

    @GetMapping("/api/v2/recipes/curations/recommended")
    public ResponseEntity<BaseResponse<CurationRecommendsResponse>> getRecommendedCurationsV2(
            @AuthenticationPrincipal LoginMember member,
            Pageable pageable
    ) {
        LocalDateTime now = LocalDateTime.now();
        CurationRecommendsResponse response = curationQueryService.getRecommendedCurationsV2(now, pageable);
        return ResponseEntity.ok(BaseResponse.success("추천 큐레이션이 조회되었습니다.", response));
    }

    @GetMapping("/api/v2/recipes/curations/{id}/search")
    public ResponseEntity<BaseResponse<RecipeCurationSearchResponse>> getCurationSearchResultV2(
            @AuthenticationPrincipal LoginMember member,
            @PathVariable long id,
            Pageable pageable
    ) {
        RecipeCurationSearchResponse response = curationQueryService.searchRecipesByCurationV2(id, pageable);
        return ResponseEntity.ok(BaseResponse.success("큐레이션 레시피 검색 결과가 조회되었습니다.", response));
    }
}
