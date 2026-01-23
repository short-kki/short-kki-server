package com.example.short_kki.support.dev.service;

import com.example.short_kki.domain.auth.dto.LoginResponse;
import com.example.short_kki.domain.file.controller.dto.FileUploadRequest;
import com.example.short_kki.domain.file.controller.dto.FileUploadResponse;
import com.example.short_kki.domain.file.entity.FileMetadata;
import com.example.short_kki.domain.file.entity.UploaderType;
import com.example.short_kki.domain.file.service.FileMetadataService;
import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.service.MemberQueryService;
import com.example.short_kki.global.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DevService {

    private final MemberQueryService memberQueryService;
    private final FileMetadataService fileMetadataService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse getLoginResponse(Long memberId) {
        Member member = memberQueryService.getById(memberId);

        String accessToken = jwtTokenProvider.createAccessToken(memberId, member.getEmail(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(memberId);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    public FileUploadResponse getFileUploadResponse(FileUploadRequest request, Long memberId) {
        FileMetadata fileMetadata = fileMetadataService.createFileMetadata(
                request.filename(),
                request.contentLength(),
                request.targetType(),
                UploaderType.MEMBER,
                memberId,
                request.visibility()
        );

        fileMetadata.markUploaded();

        return FileUploadResponse.builder()
                .fileId(fileMetadata.getId())
                .objectKey(fileMetadata.getObjectKey())
                .uploadUrl(null)
                .method(null)
                .headers(null)
                .expiresAt(null)
                .build();
    }
}
