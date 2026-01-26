package com.shortkki.api.source.service.dto;

public record SourceContentInfo(
        String externalKey,
        String title,
        String canonicalUrl,
        String thumbnailUrl,
        String channelId
) {
}