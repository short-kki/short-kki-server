package com.shortkki.api.member.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank(message = "이름은 필수입니다")
        @Size(min = 1, max = 20, message = "이름은 1~20자 이내로 입력해주세요")
        String name,
        @NotNull(message = "프로필 이미지는 필수입니다")
        Long profileImgFileId
) {
}
