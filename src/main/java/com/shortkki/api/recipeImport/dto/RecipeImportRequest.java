package com.shortkki.api.recipeImport.dto;

import jakarta.validation.constraints.NotBlank;

public record RecipeImportRequest(
        @NotBlank(message = "외부 레시피 URL은 필수입니다.") String sourceUrl) {
}
