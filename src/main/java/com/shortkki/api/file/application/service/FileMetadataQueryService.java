package com.shortkki.api.file.application.service;

import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.repository.FileMetadataRepository;
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
}
