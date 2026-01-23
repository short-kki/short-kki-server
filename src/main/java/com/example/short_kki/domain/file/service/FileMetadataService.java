package com.example.short_kki.domain.file.service;

import com.example.short_kki.domain.file.config.FileUploadProperties;
import com.example.short_kki.domain.file.entity.FileMetadata;
import com.example.short_kki.domain.file.entity.FileTargetType;
import com.example.short_kki.domain.file.entity.FileVisibility;
import com.example.short_kki.domain.file.entity.UploaderType;
import com.example.short_kki.domain.file.repository.FileMetadataRepository;
import com.example.short_kki.domain.file.util.FileNameUtil;
import com.example.short_kki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FileMetadataService {

    private final FileMetadataRepository fileMetadataRepository;
    private final FileUploadProperties fileProps;

    public FileMetadata getById(long fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 파일 입니다."));
    }

    public FileMetadata createFileMetadata(
            String filename, long size, FileTargetType targetType,
            UploaderType uploaderType, Long uploaderId, FileVisibility visibility
    ) {
        String key = generateKey(visibility, filename);
        String extension = FileNameUtil.extractSafeExtension(filename);

        FileMetadata fileMetadata = FileMetadata.createPending(
                key,
                filename,
                extension,
                size,
                targetType,
                uploaderType,
                uploaderId,
                visibility
        );

        return fileMetadataRepository.save(fileMetadata);
    }

    private String generateKey(FileVisibility visibility, String filename) {
        String prefix = (visibility == FileVisibility.PUBLIC)
                ? fileProps.publicPrefix()
                : fileProps.privatePrefix();
        return FileNameUtil.generate(prefix, filename);
    }
}

