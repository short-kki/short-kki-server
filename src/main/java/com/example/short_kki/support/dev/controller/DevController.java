package com.example.short_kki.support.dev.controller;

import com.example.short_kki.domain.auth.dto.LoginResponse;
import com.example.short_kki.domain.file.controller.dto.FileUploadRequest;
import com.example.short_kki.domain.file.controller.dto.FileUploadResponse;
import com.example.short_kki.global.auth.dto.LoginMember;
import com.example.short_kki.global.response.BaseResponse;
import com.example.short_kki.support.dev.service.DevService;
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
            @RequestParam Long memberId
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