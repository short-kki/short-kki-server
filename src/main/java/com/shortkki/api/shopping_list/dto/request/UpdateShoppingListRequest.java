package com.shortkki.api.shopping_list.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateShoppingListRequest(
        @NotBlank(message = "장볼거리 이름은 필수입니다.")
        String name
) {
}
