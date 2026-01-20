package com.example.short_kki.domain.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StepInfo(
        @NotNull(message = "순서 번호는 필수입니다")
        @Positive Integer stepOrder,
        @NotBlank(message = "단계 설명은 필수입니다")
        String description

) {

}
