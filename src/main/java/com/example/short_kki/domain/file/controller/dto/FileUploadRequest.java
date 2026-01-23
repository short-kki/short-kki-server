package com.example.short_kki.domain.file.controller.dto;

import com.example.short_kki.domain.file.entity.FileTargetType;
import com.example.short_kki.domain.file.entity.FileVisibility;
import lombok.Builder;

@Builder
public record FileUploadRequest(
        String filename,
        String contentType,
        long contentLength,
        FileVisibility visibility,
        FileTargetType targetType
) {

}
