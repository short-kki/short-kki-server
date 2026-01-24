package com.example.short_kki.domain.recipeBook.dto;

import jakarta.validation.constraints.NotNull;

public record RecipeBookAddRecipeRequest(
        @NotNull(message = "레시피 ID는 필수입니다.") Long recipeId) {
}
