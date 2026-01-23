package com.example.short_kki.domain.file.service;

import com.example.short_kki.domain.file.config.FileUploadProperties;
import com.example.short_kki.domain.file.controller.dto.FileUploadRequest;
import com.example.short_kki.domain.file.controller.dto.FileUploadResponse;
import com.example.short_kki.domain.file.entity.FileMetadata;
import com.example.short_kki.domain.file.entity.FileTargetType;
import com.example.short_kki.domain.file.entity.FileVisibility;
import com.example.short_kki.domain.file.entity.UploaderType;
import com.example.short_kki.domain.file.service.dto.UploadUrlDto;
import com.example.short_kki.domain.file.service.port.FileUploadPort;
import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.service.MemberQueryService;
import com.example.short_kki.global.error.exception.AccessDeniedException;
import com.example.short_kki.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileMetadataService fileMetadataService;
    private final MemberQueryService memberQueryService;
    private final FileUploadPort fileUploadPort;

    private final FileUploadProperties fileProps;

    public FileUploadResponse createUploadUrl(FileUploadRequest request, Long uploaderId) {
        var visibility = request.visibility();
        var contentType = request.contentType();
        var contentLength = request.contentLength();
        var filename = request.filename();
        var targetType = request.targetType();

        validateFile(visibility, contentType, contentLength, targetType);
        FileMetadata fileMetadata = fileMetadataService.createFileMetadata(
                filename, contentLength, targetType, UploaderType.MEMBER, uploaderId, visibility
        );

        UploadUrlDto result = fileUploadPort.createUploadUrl(fileMetadata.getObjectKey(), contentType, fileProps.expireSeconds());
        return FileUploadResponse.builder()
                .fileId(fileMetadata.getId())
                .objectKey(result.objectKey())
                .uploadUrl(result.uploadUrl())
                .method(result.method())
                .headers(result.headers())
                .expiresAt(result.expiresAt())
                .build();
    }

    public void markUploaded(long fileId, long memberId) {
        FileMetadata file = fileMetadataService.getById(fileId);
        Member member = memberQueryService.getById(memberId);

        if (file.getUploaderId() != member.getId()) {
            throw new AccessDeniedException("파일 업로더 id와 요청자 id가 다릅니다.");
        }

        file.markUploaded();
    }

    private void validateFile(
            FileVisibility visibility, String contentType, long contentLength, FileTargetType targetType
    ) {
        if (visibility == null) {
            throw new BadRequestException("file visibility 값이 필요합니다.");
        }
        if (contentType == null || contentType.isBlank()) {
            throw new BadRequestException("content type 값이 필요합니다.");
        }
        if (contentLength <= 0 || contentLength > fileProps.maxBytes()) {
            throw new BadRequestException("content length가 범위를 벗어났습니다.");
        }
        if (!targetType.isAllowedContentType(contentType)) {
            throw new BadRequestException("해당 파일 타입에 지원되지 않는 확장자 입니다.");
        }
    }
}
