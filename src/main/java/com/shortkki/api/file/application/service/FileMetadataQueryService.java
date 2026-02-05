package com.shortkki.api.file.application.service;

import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.UploadStatus;
import com.shortkki.api.file.repository.FileMetadataRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileMetadataQueryService {

    private final FileMetadataRepository fileMetadataRepository;

    public FileMetadata findById(long fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 파일 입니다."));
    }

    public FileMetadata findByIdWithOwnerValidation(Long fileId, Long memberId) {
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));

        if (!fileMetadata.getUploaderId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
        if (fileMetadata.getStatus() != UploadStatus.UPLOADED) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return fileMetadata;
    }
}
