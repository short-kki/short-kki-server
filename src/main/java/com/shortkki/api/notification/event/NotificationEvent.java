package com.shortkki.api.notification.event;

import com.shortkki.api.notification.entity.NotificationType;
import lombok.Builder;

@Builder
public record NotificationEvent(
        Long receiverId,
        Long senderId,
        NotificationType notificationType,
        String content,
        String relatedUrl,
        Long targetId
) {
    public static NotificationEvent of(Long receiverId, Long senderId, NotificationType notificationType,
                                       String content, String relatedUrl, Long targetId) {
        return NotificationEvent.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .notificationType(notificationType)
                .content(content)
                .relatedUrl(relatedUrl)
                .targetId(targetId)
                .build();
    }
}
