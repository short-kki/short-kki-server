package com.shortkki.api.file.controller.dto;

import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.file.entity.FileVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record FileUploadRequest(

        @NotBlank(message = "filename은 필수입니다.")
        String filename,

        @NotBlank(message = "contentType은 필수입니다.")
        String contentType,

        @Positive(message = "contentLength는 0보다 커야 합니다.")
        long contentLength,

        @NotNull(message = "visibility는 필수입니다.")
        FileVisibility visibility,

        @NotNull(message = "targetType은 필수입니다.")
        FileTargetType targetType
) {
}
