package com.shortkki.api.file.service;

import com.shortkki.api.file.config.FileUploadProperties;
import com.shortkki.api.file.controller.dto.FileUploadRequest;
import com.shortkki.api.file.controller.dto.FileUploadResponse;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.file.entity.FileVisibility;
import com.shortkki.api.file.entity.UploaderType;
import com.shortkki.api.file.service.dto.UploadUrlDto;
import com.shortkki.api.file.service.port.FileUploadPort;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.service.MemberQueryService;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import java.util.Objects;
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
                filename, contentLength, UploaderType.MEMBER, uploaderId, targetType, visibility
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
        Member member = memberQueryService.getMember(memberId);

        if (!Objects.equals(file.getUploaderId(), member.getId())) {
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
