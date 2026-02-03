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

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long sourceContentId;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Lob
    @Column(nullable = false, length = 2000)
    private String requestedSourceUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SourcePlatform platform;

    @Lob
    @Column(length = 2000)
    private String rawResponse;

    @Lob
    @Column(length = 2000)
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ImportStatus status;

    @Builder
    private SourceImportHistory(Long memberId, Long sourceContentId, Long recipeId,
            String requestedSourceUrl, SourcePlatform platform, String rawResponse,
            String errorMessage, ImportStatus status) {
        this.memberId = memberId;
        this.sourceContentId = sourceContentId;
        this.recipeId = recipeId;
        this.requestedSourceUrl = requestedSourceUrl;
        this.platform = platform;
        this.rawResponse = rawResponse;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public static SourceImportHistory create(
            Long memberId, Long sourceContentId, String requestedSourceUrl, SourcePlatform platform
    ) {
        return SourceImportHistory.builder()
                .memberId(memberId)
                .sourceContentId(sourceContentId)
                .recipeId(null)
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

    public void updateRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void fail(String errorMessage, String rawResponse) {
        this.errorMessage = errorMessage;
        this.rawResponse = rawResponse;
        this.status = ImportStatus.FAILED;
    }

    public void fail(String errorMessage) {
        this.errorMessage = errorMessage;
        this.status = ImportStatus.FAILED;
    }
}
