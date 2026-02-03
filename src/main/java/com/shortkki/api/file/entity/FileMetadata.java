package com.shortkki.api.file.entity;

import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file_metadata")
public class FileMetadata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String objectKey;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private long size;

    @Lob
    @Column(length = 2000, nullable = false)
    private String baseUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private FileTargetType targetType;

    @Column(name = "target_id")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "uploader_type")
    private UploaderType uploaderType;

    @Column(name = "uploader_id")
    private Long uploaderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_status", nullable = false)
    private UploadStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private FileVisibility visibility;

    public static FileMetadata createPending(
            String key,
            String originalName,
            String extension,
            long size,
            String baseUrl,
            FileTargetType targetType,
            UploaderType uploaderType,
            Long uploaderId,
            FileVisibility visibility
    ) {
        return FileMetadata.builder()
                .objectKey(key)
                .originalName(originalName)
                .extension(extension)
                .size(size)
                .baseUrl(baseUrl)
                .targetType(targetType)
                .uploaderType(uploaderType)
                .uploaderId(uploaderId)
                .status(UploadStatus.PENDING)
                .visibility(visibility)
                .build();
    }

    public void markUploaded() {
        this.status = UploadStatus.UPLOADED;
    }

    public void markFailed() {
        this.status = UploadStatus.FAILED;
    }

    public void bindTarget(FileTargetType targetType, Long targetId) {
        this.targetType = targetType;
        this.targetId = targetId;
    }

    public String getUrl() {
        return this.baseUrl + objectKey;
    }
}