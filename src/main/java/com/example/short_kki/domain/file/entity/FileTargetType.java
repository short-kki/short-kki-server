package com.example.short_kki.domain.file.entity;

import java.util.Set;
import lombok.Getter;

@Getter
public enum FileTargetType {

    MEMBER_PROFILE_IMG(Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    )),

    RECIPE_IMG(Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    ));

    private final Set<String> allowedContentTypes;

    FileTargetType(Set<String> allowedContentTypes) {
        this.allowedContentTypes = allowedContentTypes;
    }

    public boolean isAllowedContentType(String contentType) {
        return allowedContentTypes.contains(contentType);
    }
}