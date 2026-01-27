package com.shortkki.api.notification.dto.response;

import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.entity.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponse(
        Long id,
        Long senderId,
        NotificationType notificationType,
        String content,
        String relatedUrl,
        Long targetId,
        boolean isRead,
        LocalDateTime createdAt
) {
    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .senderId(notification.getSenderId())
                .notificationType(notification.getNotificationType())
                .content(notification.getContent())
                .relatedUrl(notification.getRelatedUrl())
                .targetId(notification.getTargetId())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
