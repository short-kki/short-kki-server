package com.shortkki.support.dev.controller;

import com.shortkki.api.auth.dto.LoginResponse;
import com.shortkki.api.file.controller.dto.FileUploadRequest;
import com.shortkki.api.file.controller.dto.FileUploadResponse;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import com.shortkki.support.dev.service.DevService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile({"local", "dev"})
@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
public class DevController {

    private final DevService devService;

    @PostMapping("/tokens")
    public ResponseEntity<BaseResponse<LoginResponse>> login(
            @RequestParam(defaultValue = "1") Long memberId
    ) {
        LoginResponse response = devService.getLoginResponse(memberId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/files/uploads")
    public ResponseEntity<BaseResponse<FileUploadResponse>> getUploadedFileId(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody FileUploadRequest request
    ) {
        FileUploadResponse response = devService.getFileUploadResponse(request, loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}