package com.shortkki.api.recipeBook.dto;

import jakarta.validation.constraints.NotNull;

public record RecipeMoveRequest(
        @NotNull Long toRecipeBookId
) {

}
