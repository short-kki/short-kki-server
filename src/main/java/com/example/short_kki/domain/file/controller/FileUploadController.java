package com.example.short_kki.domain.file.controller;

import com.example.short_kki.domain.file.controller.dto.FileUploadRequest;
import com.example.short_kki.domain.file.controller.dto.FileUploadResponse;
import com.example.short_kki.domain.file.service.FileUploadService;
import com.example.short_kki.global.auth.dto.LoginMember;
import com.example.short_kki.global.response.BaseResponse;
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
    public ResponseEntity<BaseResponse<FileUploadResponse>> createUploadUrl(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody FileUploadRequest request
    ) {
        FileUploadResponse response = fileUploadService.createUploadUrl(request, loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PatchMapping("/uploads/{fileId}")
    public ResponseEntity<BaseResponse<Void>> createUploadUrl(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long fileId
    ) {
        fileUploadService.markUploaded(fileId, loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success());
    }
}
