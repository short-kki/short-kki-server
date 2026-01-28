package com.shortkki.api.source.domain;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "source_import_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SourceImportHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sourceContentId;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String requestedSourceUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SourcePlatform platform;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String rawResponse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ImportStatus status;

    @Builder
    private SourceImportHistory(Long sourceContentId, String requestedSourceUrl,
            SourcePlatform platform, String rawResponse, ImportStatus status) {
        this.sourceContentId = sourceContentId;
        this.requestedSourceUrl = requestedSourceUrl;
        this.platform = platform;
        this.rawResponse = rawResponse;
        this.status = status;
    }

    public static SourceImportHistory create(
            Long sourceContentId, String requestedSourceUrl, SourcePlatform platform
    ) {
        return SourceImportHistory.builder()
                .sourceContentId(sourceContentId)
                .requestedSourceUrl(requestedSourceUrl)
                .platform(platform)
                .status(ImportStatus.REQUESTED)
                .build();
    }

    public void updateStatus(ImportStatus status) {
        this.status = status;
    }

    public void updateRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public void complete(String rawResponse) {
        this.rawResponse = rawResponse;
        this.status = ImportStatus.COMPLETED;
    }

    public void fail(String rawResponse) {
        this.rawResponse = rawResponse;
        this.status = ImportStatus.FAILED;
    }
}
