package com.example.short_kki.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGroupRequest(
        @NotBlank(message = "그룹 이름은 필수입니다.")
        @Size(max = 50, message = "그룹 이름은 50자 이내여야 합니다.")
        String name,

        @Size(max = 500, message = "그룹 설명은 500자 이내여야 합니다.")
        String description,

        String thumbnailImgUrl
) {
}
