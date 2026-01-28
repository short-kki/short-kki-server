package com.shortkki.api.source.domain;

import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "source_content",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_SOURCE_CONTENT_PLATFORM_KEY",
                columnNames = {"platform", "external_key"}
        ))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SourceContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_author_id", nullable = false)
    private SourceContentCreator sourceCreator;

    @Column(name = "external_key", nullable = false, length = 100)
    private String externalKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SourcePlatform platform;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 500)
    private String canonicalUrl;

    @Column(length = 500)
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SourceContentType contentType;

    @Column(nullable = false)
    private boolean isActive = true;

    @Builder
    private SourceContent(SourceContentCreator sourceCreator, String externalKey,
            SourcePlatform platform, String title, String canonicalUrl,
            String thumbnailUrl, SourceContentType contentType) {
        this.sourceCreator = sourceCreator;
        this.externalKey = externalKey;
        this.platform = platform;
        this.title = title;
        this.canonicalUrl = canonicalUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.contentType = contentType;
        this.isActive = true;
    }

    public static SourceContent create(
            String title, String canonicalUrl, String externalKey, SourcePlatform platform,
            String thumbnailUrl, SourceContentType contentType, SourceContentCreator sourceCreator
    ) {
        return SourceContent.builder()
                .sourceCreator(sourceCreator)
                .externalKey(externalKey)
                .platform(platform)
                .title(title)
                .canonicalUrl(canonicalUrl)
                .thumbnailUrl(thumbnailUrl)
                .contentType(contentType)
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }
}
