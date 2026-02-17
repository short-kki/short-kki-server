package com.shortkki.api.notification.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record FcmTokenDeleteRequest(
        @NotBlank(message = "FCM 토큰은 필수입니다.")
        String fcmToken
) {
}
