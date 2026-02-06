package com.shortkki.api.file.application.service;

import com.shortkki.api.file.config.FileUploadProperties;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.file.entity.FileVisibility;
import com.shortkki.api.file.entity.UploaderType;
import com.shortkki.api.file.repository.FileMetadataRepository;
import com.shortkki.api.file.util.FileKeyUtil;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileMetadataService {

    private final FileMetadataRepository fileMetadataRepository;
    private final FileUploadProperties fileProps;
    private final String baseUrl;
    private final String defaultPath;

    public FileMetadataService(
            @Value("${file.cdn.public-base-url}") String baseUrl,
            @Value("${file.cdn.default-path}") String defaultPath,
            FileMetadataRepository fileMetadataRepository,
            FileUploadProperties fileProps
    ) {
        this.baseUrl = baseUrl;
        this.fileMetadataRepository = fileMetadataRepository;
        this.fileProps = fileProps;
        this.defaultPath = defaultPath;
    }

    public FileMetadata createFileMetadata(
            String filename, long size, UploaderType uploaderType, Long uploaderId,
            FileTargetType targetType, FileVisibility visibility
    ) {
        String prefix = generatePrefix(LocalDate.now(), targetType, visibility);
        String key = FileKeyUtil.generateKey(prefix, filename);
        String extension = FileKeyUtil.extractSafeExtension(filename);
        String publicUrl = resolvePublicUrl(visibility, key);

        FileMetadata fileMetadata = FileMetadata.createPending(
                key,
                filename,
                extension,
                size,
                targetType,
                uploaderType,
                uploaderId,
                visibility,
                publicUrl
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

    private String resolvePublicUrl(FileVisibility visibility, String key) {
        if (FileVisibility.PRIVATE.equals(visibility)) {
            return null;
        }

        String normalizedKey = key.startsWith(defaultPath)
                ? key.substring(defaultPath.length())
                : key;

        return baseUrl + normalizedKey;
    }
}