package com.shortkki.api.file.controller;

import com.shortkki.api.file.controller.dto.FileUploadRequest;
import com.shortkki.api.file.controller.dto.FileUploadResponse;
import com.shortkki.api.file.application.service.FileUploadService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/uploads")
    public ResponseEntity<BaseResponse<FileUploadResponse>> markUploaded(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody FileUploadRequest request
    ) {
        FileUploadResponse response = fileUploadService.createUploadUrl(request, loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PatchMapping("/uploads/{fileId}")
    public ResponseEntity<BaseResponse<Void>> markUploaded(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long fileId
    ) {
        fileUploadService.markUploaded(fileId, loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success());
    }
}
