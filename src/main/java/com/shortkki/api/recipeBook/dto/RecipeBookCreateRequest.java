package com.shortkki.api.recipeBook.dto;

import jakarta.validation.constraints.NotBlank;

public record RecipeBookCreateRequest(
        @NotBlank(message = "레시피북 제목은 필수입니다.")
        String title
) {

}
