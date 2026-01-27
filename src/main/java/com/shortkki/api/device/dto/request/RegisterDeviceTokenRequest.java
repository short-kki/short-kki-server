package com.shortkki.api.device.dto.request;

import com.shortkki.api.device.entity.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDeviceTokenRequest(
        @NotBlank(message = "디바이스 토큰은 필수입니다.")
        String token,

        @NotNull(message = "디바이스 타입은 필수입니다.")
        DeviceType deviceType
) {
}
