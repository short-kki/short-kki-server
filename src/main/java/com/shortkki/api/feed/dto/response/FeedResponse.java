package com.shortkki.api.feed.dto.response;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.FeedType;
import com.shortkki.api.recipeBook.dto.RecipeSummaryResponse;
import java.time.LocalDateTime;

public record FeedResponse(
        Long id,
        String content,
        FeedType feedType,
        Long authorId,
        String authorName,
        Long likes,
        boolean likedByMe,
        Long imageFileId,
        String imageUrl,
        RecipeSummaryResponse recipe,
        LocalDateTime createdAt
) {

    public static FeedResponse from(Feed feed, boolean likedByMe) {
        return new FeedResponse(
                feed.getId(),
                feed.getContent(),
                feed.getFeedType(),
                feed.getMember().getId(),
                feed.getMember().getName(),
                feed.getLikes(),
                likedByMe,
                feed.getImage() != null ? feed.getImage().getId() : null,
                feed.getImageUrl(),
                feed.getRecipe() != null
                        ? RecipeSummaryResponse.from(feed.getRecipe())
                        : null,
                feed.getCreatedAt()
        );
    }
}
