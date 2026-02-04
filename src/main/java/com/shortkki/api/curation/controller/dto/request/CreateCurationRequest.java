package com.shortkki.api.curation.controller.dto.request;

import com.shortkki.api.curation.entity.DayType;
import com.shortkki.api.curation.entity.TimeType;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CreateCurationRequest(
        @NotBlank(message = "큐레이션 제목은 필수입니다.")
        @Size(max = 100, message = "큐레이션 제목은 100자 이내여야 합니다.")
        String title,

        @Size(max = 500, message = "큐레이션 설명은 500자 이내여야 합니다.")
        String description,

        Set<DayType> dayTypes,
        Set<TimeType> timeTypes,
        Set<CuisineType> cuisineTypes,
        Set<MealType> mealTypes,
        Set<Difficulty> difficulties,
        Set<String> keywords,
        Set<String> tags,
        Set<String> ingredients
) {
}
