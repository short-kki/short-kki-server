package com.shortkki.api.curation.controller;

import com.shortkki.api.curation.dto.request.CreateCurationRequest;
import com.shortkki.api.curation.dto.response.CurationRecommendResponse;
import com.shortkki.api.curation.dto.response.CurationResponse;
import com.shortkki.api.curation.service.CurationQueryService;
import com.shortkki.api.curation.service.CurationService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class CurationController {

    private final CurationService curationService;
    private final CurationQueryService curationQueryService;

    @PostMapping("/api/v1/curations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<CurationResponse>> create(
            @Valid @RequestBody CreateCurationRequest request
    ) {
        CurationResponse response = curationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("큐레이션이 생성되었습니다.", response));
    }

    @GetMapping("/api/v1/curations/recommended")
    public ResponseEntity<BaseResponse<List<CurationRecommendResponse>>> getRecommendedCurations(
            @AuthenticationPrincipal LoginMember member
    ) {
        LocalDateTime now = LocalDateTime.now();
        List<CurationRecommendResponse> response = curationQueryService.getRecommendedCurations(now);
        return ResponseEntity.ok(BaseResponse.success("추천 큐레이션이 조회되었습니다.", response));
    }

    @GetMapping("/api/v2/curations/recommended")
    public ResponseEntity<BaseResponse<List<CurationRecommendResponse>>> getRecommendedCurationsV2(
            @AuthenticationPrincipal LoginMember member
    ) {
        LocalDateTime now = LocalDateTime.now();
        List<CurationRecommendResponse> response = curationQueryService.getRecommendedCurationsV2(now);
        return ResponseEntity.ok(BaseResponse.success("추천 큐레이션이 조회되었습니다.", response));
    }
}
