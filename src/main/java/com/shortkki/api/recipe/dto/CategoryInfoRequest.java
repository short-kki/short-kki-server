package com.shortkki.api.recipe.dto;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import jakarta.validation.constraints.NotNull;

public record CategoryInfoRequest(
        @NotNull(message = "요리 종류는 필수입니다") CuisineType cuisineType,

        @NotNull(message = "식사 유형은 필수입니다") MealType mealType,

        @NotNull(message = "난이도는 필수입니다") Difficulty difficulty
) {

}
