package com.shortkki.api.notification.controller.dto;

public record UnreadCountResponse(
        long count
) {
    public static UnreadCountResponse of(long count) {
        return new UnreadCountResponse(count);
    }
}
