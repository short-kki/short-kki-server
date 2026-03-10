package com.shortkki.api.curation.controller;

import com.shortkki.api.curation.controller.dto.request.CreateCurationRequest;
import com.shortkki.api.curation.controller.dto.response.CurationRecommendsResponse;
import com.shortkki.api.curation.controller.dto.response.CurationResponse;
import com.shortkki.api.curation.controller.dto.response.RecipeCurationSearchResponse;
import com.shortkki.api.curation.service.CurationQueryService;
import com.shortkki.api.curation.service.CurationQueryServiceV2;
import com.shortkki.api.curation.service.CurationService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/recipes/curations")
public class CurationController {

    private final CurationService curationService;
    private final CurationQueryService curationQueryService;
    private final CurationQueryServiceV2 curationQueryServiceV2;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<CurationResponse>> create(
            @Valid @RequestBody CreateCurationRequest request
    ) {
        CurationResponse response = curationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("큐레이션이 생성되었습니다.", response));
    }

    @GetMapping("/recommended")
    public ResponseEntity<BaseResponse<CurationRecommendsResponse>> getRecommendedCurations(
            @AuthenticationPrincipal LoginMember member,
            Pageable pageable
    ) {
        LocalDateTime now = LocalDateTime.now();
        CurationRecommendsResponse response = curationQueryService.getRecommendedCurations(member.getId(), now, pageable);
        return ResponseEntity.ok(BaseResponse.success("추천 큐레이션이 조회되었습니다.", response));
    }

    @GetMapping("/{id}/search")
    public ResponseEntity<BaseResponse<RecipeCurationSearchResponse>> getCurationSearchResult(
            @AuthenticationPrincipal LoginMember member,
            @PathVariable long id,
            Pageable pageable
    ) {
        RecipeCurationSearchResponse response = curationQueryService.searchRecipesByCuration(member.getId(), id, pageable);
        return ResponseEntity.ok(BaseResponse.success("큐레이션 레시피 검색 결과가 조회되었습니다.", response));
    }

    @GetMapping("/top")
    public ResponseEntity<BaseResponse<RecipeCurationSearchResponse>> getTopCurationSearchResult(
            @AuthenticationPrincipal LoginMember member,
            Pageable pageable
    ) {
        RecipeCurationSearchResponse response = curationQueryServiceV2.searchTopCuration(member.getId(), pageable);
        return ResponseEntity.ok(BaseResponse.success("Top 큐레이션 검색 결과가 조회되었습니다.", response));
    }
}
