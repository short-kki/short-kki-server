package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.constant.CuisineType;
import com.example.short_kki.domain.recipe.constant.Difficulty;
import com.example.short_kki.domain.recipe.constant.MealType;
import jakarta.validation.constraints.NotNull;

public record CategoryInfoRequest(
        @NotNull(message = "요리 종류는 필수입니다") CuisineType cuisineType,

        @NotNull(message = "식사 유형은 필수입니다") MealType mealType,

        @NotNull(message = "난이도는 필수입니다") Difficulty difficulty
) {

}
