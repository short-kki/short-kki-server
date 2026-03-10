package com.shortkki.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @NotBlank(message = "refreshToken은 비어있을 수 없습니다.")
        String refreshToken
) {
}
