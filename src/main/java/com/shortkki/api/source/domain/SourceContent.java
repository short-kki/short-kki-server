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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "source_content",
        uniqueConstraints = @UniqueConstraint(columnNames = {"platform", "external_key"}))
@NamedEntityGraph(
        name = "SourceContent.withCreator",
        attributeNodes = @NamedAttributeNode("sourceCreator")
)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class SourceContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_author_id", nullable = false)
    private SourceContentCreator sourceCreator;

    @Column(name = "external_key", nullable = false, length = 100, unique = true)
    private String externalKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SourcePlatform platform;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 500, unique = true)
    private String canonicalUrl;

    @Column(length = 500, unique = true)
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SourceContentType contentType;

    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;

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
