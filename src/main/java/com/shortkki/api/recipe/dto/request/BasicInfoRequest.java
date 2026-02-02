package com.shortkki.api.recipe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BasicInfoRequest(
        @NotBlank(message = "제목은 필수입니다") @Size(max = 100, message = "제목은 100자 이하여야 합니다") String title,

        @Size(max = 500, message = "설명은 500자 이하여야 합니다") String description,

        @NotNull(message = "1인분 기준 양은 필수입니다") @Positive(message = "1인분 기준 양은 양수여야 합니다") Integer servingSize,

        @NotNull(message = "조리 시간은 필수입니다") @Positive(message = "조리 시간은 양수여야 합니다") Integer cookingTime,

        Long imageFileId) {

}
