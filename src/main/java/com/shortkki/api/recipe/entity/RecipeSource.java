package com.shortkki.api.recipe.entity;

import com.shortkki.api.recipe.constant.SourceContentType;
import com.shortkki.api.recipe.constant.SourcePlatform;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class RecipeSource {

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SourcePlatform platform;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SourceContentType contentType;

    public RecipeSource(String url, SourcePlatform platform, SourceContentType contentType) {
        validate(url, platform, contentType);
        this.url = url;
        this.platform = platform;
        this.contentType = contentType;
    }

    private void validate(String url, SourcePlatform platform, SourceContentType contentType) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("출처 URL은 필수입니다.");
        }
        if (platform == null) {
            throw new IllegalArgumentException("출처 플랫폼은 필수입니다.");
        }
        if (contentType == null) {
            throw new IllegalArgumentException("출처 컨텐츠 유형은 필수입니다.");
        }
    }
}
