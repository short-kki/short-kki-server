package com.shortkki.support.dev.service;

import com.shortkki.api.auth.dto.LoginResponse;
import com.shortkki.api.file.controller.dto.FileUploadRequest;
import com.shortkki.api.file.controller.dto.FileUploadResponse;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.UploaderType;
import com.shortkki.api.file.service.FileMetadataService;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.service.MemberQueryService;
import com.shortkki.global.auth.jwt.JwtTokenProvider;
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
        Member member = memberQueryService.getMember(memberId);

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
                UploaderType.MEMBER,
                memberId,
                request.targetType(),
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
