package com.shortkki.api.feed.dto.response;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.FeedType;

import java.time.LocalDateTime;

public record FeedResponse(
        Long id,
        String content,
        FeedType feedType,
        Long authorId,
        String authorName,
        LocalDateTime createdAt
) {

    public static FeedResponse from(Feed feed) {
        return new FeedResponse(
                feed.getId(),
                feed.getContent(),
                feed.getFeedType(),
                feed.getMember().getId(),
                feed.getMember().getName(),
                feed.getCreatedAt()
        );
    }
}
