package com.shortkki.api.shopping_list.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ShoppingListItemRequest(
        @NotBlank(message = "재료 이름은 필수입니다.")
        String name
) {
}
