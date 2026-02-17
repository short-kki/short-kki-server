package com.shortkki.api.notification.controller.dto;

import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.entity.NotificationType;
import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        NotificationType type,
        String content,
        Long targetId,
        String payload,
        Boolean isRead,
        LocalDateTime createdAt
) {
    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getNotificationType(),
                notification.getContent(),
                notification.getTargetId(),
                notification.getPayload(),
                notification.getIsRead(),
                notification.getCreatedAt()
        );
    }
}
