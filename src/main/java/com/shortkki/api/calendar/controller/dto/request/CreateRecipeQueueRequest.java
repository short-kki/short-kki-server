package com.shortkki.api.calendar.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateRecipeQueueRequest(
        @NotNull(message = "레시피 ID는 필수입니다.")
        Long recipeId
) {
}
