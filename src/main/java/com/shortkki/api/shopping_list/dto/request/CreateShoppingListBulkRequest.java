package com.shortkki.api.shopping_list.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreateShoppingListBulkRequest(
        @NotEmpty(message = "장볼거리 이름 목록은 필수입니다.")
        List<String> names
) {
}
