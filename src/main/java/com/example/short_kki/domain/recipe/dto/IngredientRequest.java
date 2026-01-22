package com.example.short_kki.domain.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequest(
        @NotBlank(message = "재료명은 필수입니다") String name,
        @NotNull(message = "단위는 필수입니다") String unit,
        Integer amount) {
}
