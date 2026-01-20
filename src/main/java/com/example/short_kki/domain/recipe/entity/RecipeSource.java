package com.example.short_kki.domain.recipe.entity;

import com.example.short_kki.domain.recipe.constant.SourceContentType;
import com.example.short_kki.domain.recipe.constant.SourcePlatform;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record RecipeSource(
        @Column(nullable = false)
        String url,

        @Column(nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        SourcePlatform platform,

        @Column(nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        SourceContentType contentType

) {

    public RecipeSource {
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
