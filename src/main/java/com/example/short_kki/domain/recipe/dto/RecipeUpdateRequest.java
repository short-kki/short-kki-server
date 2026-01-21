package com.example.short_kki.domain.recipe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 레시피 수정 요청 DTO - 수동 입력(USER_CREATED) 레시피만 수정 가능 - 외부 출처(IMPORTED) 레시피는 수정 불가
 */
public record RecipeUpdateRequest(
        @Valid
        @NotNull(message = "기본 정보는 필수입니다")
        BasicInfo basicInfo,

        @Valid
        @NotNull(message = "카테고리 정보는 필수입니다")
        CategoryInfo categoryInfo,

        @Valid
        @NotNull(message = "재료는 필수입니다.")
        @Size(min = 1, message = "재료는 최소 1개 이상이어야 합니다.")
        List<IngredientInfo> ingredients,

        @Valid
        @NotNull(message = "조리 순서는 필수입니다.")
        @Size(min = 1, message = "조리 순서는 최소 1개 이상이어야 합니다.")
        List<StepInfo> steps) {

}
