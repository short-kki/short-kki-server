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
    @Column(nullable = false, columnDefinition = "TEXT")
    private String requestedSourceUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SourcePlatform platform;

    /**
     * 외부 응답 원본 문자열 - 파싱 성공/실패와 무관 - 디버깅 및 재처리 용도
     */
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String rawResponse;

    /**
     * 파싱된 결과물 - PARSED 상태에서 존재 (사용자 확인/수정용) - COMPLETED 전환 시 정리(null)될 수 있음
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String parsedContent;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ImportStatus status;

    @Builder
    private SourceImportHistory(Long memberId, Long sourceContentId, Long recipeId,
            String requestedSourceUrl, SourcePlatform platform, String rawResponse,
            String parsedContent, String errorMessage, ImportStatus status) {
        this.memberId = memberId;
        this.sourceContentId = sourceContentId;
        this.recipeId = recipeId;
        this.requestedSourceUrl = requestedSourceUrl;
        this.platform = platform;
        this.rawResponse = rawResponse;
        this.parsedContent = parsedContent;
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

    /**
     * 파싱 성공 - rawResponse / parsedContent 저장 - status = PARSED - errorMessage 정리
     */
    public void markAsParsed(String rawResponse, String parsedContent) {
        if (parsedContent == null || parsedContent.isBlank()) {
            throw new IllegalArgumentException("파싱된 결과가 비어있지 않아야한다.");
        }
        this.rawResponse = rawResponse;
        this.parsedContent = parsedContent;
        this.errorMessage = null;
        this.status = ImportStatus.PARSED;
    }

    /**
     * 저장 확정(최종 완료) - recipeId 기록 - status = COMPLETED - parsedContent 정리(요구사항) - errorMessage 정리
     */
    public void complete(Long recipeId) {
        this.recipeId = recipeId;
        this.status = ImportStatus.COMPLETED;
        this.errorMessage = null;
        this.parsedContent = null;
    }

    public void updateRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void fail(String errorMessage, String rawResponse) {
        this.errorMessage = errorMessage;
        this.rawResponse = rawResponse;
        this.parsedContent = null;
        this.status = ImportStatus.FAILED;
    }

    public void fail(String errorMessage) {
        this.errorMessage = errorMessage;
        this.parsedContent = null;
        this.status = ImportStatus.FAILED;
    }
}
