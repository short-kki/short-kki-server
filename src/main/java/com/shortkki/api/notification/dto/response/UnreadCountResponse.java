package com.shortkki.api.notification.dto.response;

public record UnreadCountResponse(
        long unreadCount
) {
    public static UnreadCountResponse of(long unreadCount) {
        return new UnreadCountResponse(unreadCount);
    }
}
