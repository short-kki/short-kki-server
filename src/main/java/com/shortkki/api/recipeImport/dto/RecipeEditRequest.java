package com.shortkki.api.recipeImport.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RecipeEditRequest(
        @NotBlank(message = "레시피 제목은 필수입니다")
        @Size(max = 100, message = "레시피 제목은 100자를 초과할 수 없습니다")
        String title,

        @Size(max = 500, message = "레시피 설명은 500자를 초과할 수 없습니다")
        String description,

        @NotNull(message = "인분은 필수입니다")
        @Min(value = 1, message = "인분은 1 이상이어야 합니다")
        Integer servingSize,

        @NotNull(message = "조리 시간은 필수입니다")
        @Min(value = 0, message = "조리 시간은 0 이상이어야 합니다")
        Integer cookingTime,

        String cuisineType,
        String mealType,
        String difficulty,

        @NotEmpty(message = "재료는 최소 1개 이상이어야 합니다")
        @Valid
        List<IngredientEditDto> ingredients,

        @NotEmpty(message = "조리 순서는 최소 1개 이상이어야 합니다")
        @Valid
        List<StepEditDto> steps
) {

    public record IngredientEditDto(
            @NotBlank(message = "재료명은 필수입니다")
            @Size(max = 100, message = "재료명은 100자를 초과할 수 없습니다")
            String name,

            String amount,

            @NotBlank(message = "단위는 필수입니다")
            @Size(max = 50, message = "단위는 50자를 초과할 수 없습니다")
            String unit
    ) {
    }

    public record StepEditDto(
            @NotNull(message = "조리 순서 번호는 필수입니다")
            @Min(value = 1, message = "조리 순서 번호는 1 이상이어야 합니다")
            Integer stepNumber,

            @NotBlank(message = "조리 설명은 필수입니다")
            @Size(max = 1000, message = "조리 설명은 1000자를 초과할 수 없습니다")
            String description
    ) {
    }
}
