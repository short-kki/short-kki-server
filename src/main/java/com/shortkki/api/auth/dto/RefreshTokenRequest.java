package com.shortkki.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "accessToken은 비어있을 수 없습니다.")
        String accessToken,
        @NotBlank(message = "refreshToken은 비어있을 수 없습니다.")
        String refreshToken
) {

}
