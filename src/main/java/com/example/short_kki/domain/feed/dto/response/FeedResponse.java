package com.example.short_kki.domain.feed.dto.response;

import com.example.short_kki.domain.feed.entity.Feed;

import java.time.LocalDateTime;

public record FeedResponse(
        Long id,
        String content,
        Long authorId,
        String authorName,
        LocalDateTime createdAt
) {

    public static FeedResponse from(Feed feed) {
        return new FeedResponse(
                feed.getId(),
                feed.getContent(),
                feed.getMember().getId(),
                feed.getMember().getName(),
                feed.getCreatedAt()
        );
    }
}
