package com.example.short_kki.domain.file.service.dto;

import java.time.Instant;
import java.util.Map;
import lombok.Builder;

@Builder
public record UploadUrlDto(
        String objectKey,
        String uploadUrl,
        String method,
        Map<String, String> headers,
        Instant expiresAt
) {
}
