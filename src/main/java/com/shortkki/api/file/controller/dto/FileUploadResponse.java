package com.shortkki.api.file.controller.dto;

import java.time.Instant;
import java.util.Map;
import lombok.Builder;

@Builder
public record FileUploadResponse(
        Long fileId,
        String objectKey,
        String uploadUrl,
        String method,
        Map<String, String> headers,
        Instant expiresAt
) {

}
