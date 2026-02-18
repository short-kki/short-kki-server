package com.shortkki.api.notification.application.dto;

import com.shortkki.api.notification.entity.NotificationType;
import java.util.Map;

public record PushMessage(
        String title,
        String body,
        NotificationType type,
        Long targetId,
        Map<String, String> data
) {
    public static PushMessage of(String title, String body, NotificationType type, Long targetId) {
        return new PushMessage(title, body, type, targetId, Map.of());
    }

    public static PushMessage of(String title, String body, NotificationType type, Long targetId, Map<String, String> data) {
        return new PushMessage(title, body, type, targetId, data);
    }
}
