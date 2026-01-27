package com.shortkki.api.device.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteDeviceTokenRequest(
        @NotBlank(message = "디바이스 토큰은 필수입니다.")
        String token
) {
}
