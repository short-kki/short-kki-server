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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "source_content_creator",
        uniqueConstraints = @UniqueConstraint(columnNames = {"platform", "external_key"}))
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
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
    private String profileImgUrl;

    public static SourceContentCreator create(
            String externalKey, SourcePlatform platform, String displayName, String profileImgUrl
    ) {
        return SourceContentCreator.builder()
                .externalKey(externalKey)
                .platform(platform)
                .displayName(displayName)
                .profileImgUrl(profileImgUrl)
                .build();
    }
}
