package com.shortkki.api.recipe.dto;

import jakarta.validation.constraints.NotBlank;

public record StepRequest(
        @NotBlank(message = "단계 설명은 필수입니다")
        String description
) {

}
