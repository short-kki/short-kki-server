package com.shortkki.api.contentFeedback.controller;

import com.shortkki.api.contentFeedback.dto.request.CreateContentFeedbackRequest;
import com.shortkki.api.contentFeedback.service.ContentFeedbackService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class ContentFeedbackController {

    private final ContentFeedbackService contentFeedbackService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createContentFeedback(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody CreateContentFeedbackRequest request
    ) {
        contentFeedbackService.createFeedback(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }
}
