package com.shortkki.api.notification.controller.dto;

import com.shortkki.global.entity.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FcmTokenRegisterRequest(
        @NotBlank(message = "FCM 토큰은 필수입니다.")
        String fcmToken,

        String deviceId,

        @NotNull(message = "플랫폼은 필수입니다.")
        Platform platform
) {
}
