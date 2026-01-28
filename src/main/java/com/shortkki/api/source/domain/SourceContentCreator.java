package com.shortkki.api.source.domain;

import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "source_content_creator",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_SOURCE_CREATOR_PLATFORM_KEY",
                columnNames = {"platform", "external_key"}
        ))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SourceContentCreator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_key", nullable = false, length = 100)
    private String externalKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SourcePlatform platform;

    @Column(nullable = false, length = 100)
    private String displayName;

    @Column(length = 500)
    private String thumbnailUrl;

    @Builder
    private SourceContentCreator(String externalKey, SourcePlatform platform,
            String displayName, String thumbnailUrl) {
        this.externalKey = externalKey;
        this.platform = platform;
        this.displayName = displayName;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static SourceContentCreator create(String externalKey, SourcePlatform platform,
            String displayName, String thumbnailUrl) {
        return SourceContentCreator.builder()
                .externalKey(externalKey)
                .platform(platform)
                .displayName(displayName)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
