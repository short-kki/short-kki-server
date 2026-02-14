package com.shortkki.api.notification.controller.dto;

import com.shortkki.api.notification.entity.Notification;
import java.util.List;
import org.springframework.data.domain.Slice;

public record NotificationSliceResponse(
        List<NotificationResponse> content,
        Long nextCursor,
        boolean hasNext
) {
    public static NotificationSliceResponse from(Slice<Notification> slice) {
        List<NotificationResponse> content = slice.getContent().stream()
                .map(NotificationResponse::from)
                .toList();

        Long nextCursor = null;
        if (slice.hasNext() && !content.isEmpty()) {
            nextCursor = content.get(content.size() - 1).id();
        }

        return new NotificationSliceResponse(content, nextCursor, slice.hasNext());
    }
}
