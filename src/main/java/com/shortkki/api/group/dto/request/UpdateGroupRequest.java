package com.shortkki.api.group.dto.request;

import com.shortkki.api.group.entity.GroupType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateGroupRequest(
        @NotBlank(message = "그룹 이름은 필수입니다.")
        @Size(max = 50, message = "그룹 이름은 50자 이내여야 합니다.")
        String name,

        @Size(max = 500, message = "그룹 설명은 500자 이내여야 합니다.")
        String description,

        String thumbnailImgUrl,

        @NotNull(message = "그룹 타입은 필수입니다.")
        GroupType groupType
) {
}
