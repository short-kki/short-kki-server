package com.shortkki.api.recipeBook.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record RecipeBookReorderRequest(
        @NotEmpty(message = "레시피북 ID 목록은 비어있을 수 없습니다") List<Long> recipeBookIds) {

}
