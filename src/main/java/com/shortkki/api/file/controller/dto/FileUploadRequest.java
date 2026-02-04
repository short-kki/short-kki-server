package com.shortkki.api.file.controller.dto;

import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.file.entity.FileVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record FileUploadRequest(

        @Schema(description = "파일명", example = "sample.png")
        @NotBlank(message = "filename은 필수입니다.")
        String filename,

        @Schema(description = "파일 Content-Type", example = "img/png")
        @NotBlank(message = "contentType은 필수입니다.")
        String contentType,

        @Schema(description = "파일 크기(byte)", example = "5000")
        @Positive(message = "contentLength는 0보다 커야 합니다.")
        long contentLength,

        @Schema(description = "파일 공개 범위", example = "PUBLIC")
        @NotNull(message = "visibility는 필수입니다.")
        FileVisibility visibility,

        @Schema(description = "파일 대상 타입", example = "RECIPE_IMG")
        @NotNull(message = "targetType은 필수입니다.")
        FileTargetType targetType
) {

}
