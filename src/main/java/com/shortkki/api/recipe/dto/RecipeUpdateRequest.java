package com.shortkki.api.recipe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


public record RecipeUpdateRequest(
        @Valid @NotNull(message = "기본 정보는 필수입니다")
        BasicInfoRequest basicInfo,

        @Valid @NotNull(message = "카테고리 정보는 필수입니다")
        CategoryInfoRequest categoryInfo,

        @Valid @NotNull(message = "재료는 필수입니다.")
        @Size(min = 1, message = "재료는 최소 1개 이상이어야 합니다.")
        List<IngredientRequest> ingredients,

        @Valid @NotNull(message = "조리 순서는 필수입니다.")
        @Size(min = 1, message = "조리 순서는 최소 1개 이상이어야 합니다.")
        List<StepRequest> steps
) {

}
