package com.example.short_kki.domain.file.service;

import com.example.short_kki.domain.file.config.FileUploadProperties;
import com.example.short_kki.domain.file.entity.FileMetadata;
import com.example.short_kki.domain.file.entity.FileTargetType;
import com.example.short_kki.domain.file.entity.FileVisibility;
import com.example.short_kki.domain.file.entity.UploaderType;
import com.example.short_kki.domain.file.repository.FileMetadataRepository;
import com.example.short_kki.domain.file.util.FileKeyUtil;
import com.example.short_kki.global.error.exception.NotFoundException;
import java.time.LocalDate;
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
            String filename, long size, UploaderType uploaderType, Long uploaderId,
            FileTargetType targetType, FileVisibility visibility
    ) {
        String prefix = generatePrefix(LocalDate.now(), targetType, visibility);
        String key = FileKeyUtil.generateKey(prefix, filename);
        String extension = FileKeyUtil.extractSafeExtension(filename);

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

    private String generatePrefix(LocalDate today, FileTargetType targetType, FileVisibility visibility) {
        String root = (visibility == FileVisibility.PUBLIC)
                ? fileProps.publicPrefix()
                : fileProps.privatePrefix();

        return FileKeyUtil.joinPrefix(
                root,
                targetType.getPrefix(),
                String.valueOf(today.getYear()),
                String.valueOf(today.getMonthValue())
        );
    }
}