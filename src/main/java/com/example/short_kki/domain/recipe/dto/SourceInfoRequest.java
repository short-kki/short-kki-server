package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.constant.SourceContentType;
import com.example.short_kki.domain.recipe.constant.SourcePlatform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SourceInfoRequest(
        @NotBlank(message = "출처 URL은 필수입니다")
        String sourceUrl,

        @NotNull(message = "출처 플랫폼은 필수입니다")
        SourcePlatform sourcePlatform,

        @NotNull(message = "출처 컨텐츠 유형은 필수입니다")
        SourceContentType sourceContentType
) {

}
