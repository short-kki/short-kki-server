package com.shortkki.api.file.entity;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FileTargetType {

    MEMBER_PROFILE_IMG(
            Set.of("image/jpeg", "image/png", "image/webp", "image/heic", "image/heif"),
            "member/profile"
    ),

    RECIPE_IMG(
            Set.of("image/jpeg", "image/png", "image/webp", "image/heic", "image/heif"),
            "recipe"
    ),
    FEED_IMG(  Set.of("image/jpeg", "image/png", "image/webp", "image/heic", "image/heif"),
            "feed"
    ),
    ;

    private final Set<String> allowedContentTypes;
    private final String prefix;

    public boolean isAllowedContentType(String contentType) {
        if (contentType == null) {
            return false;
        }

        return allowedContentTypes.contains(contentType.toLowerCase());
    }
}