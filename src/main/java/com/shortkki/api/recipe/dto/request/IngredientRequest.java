package com.shortkki.api.recipe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequest(
        @NotBlank(message = "재료명은 필수입니다") String name,
        @NotBlank(message = "단위는 필수입니다") String unit,
        @NotNull(message = "수량은 필수입니다(소수점 입력 가능)") Double amount
) {

}
