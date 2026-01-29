package com.shortkki.api.recipeBook.dto;

import jakarta.validation.constraints.NotBlank;

public record RecipeBookUpdateRequest(
        @NotBlank(message = "레시피북 제목은 필수입니다.")
        String title
) {

}
