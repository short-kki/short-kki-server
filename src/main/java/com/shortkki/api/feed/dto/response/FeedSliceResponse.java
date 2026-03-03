package com.shortkki.api.feed.dto.response;

import com.shortkki.api.feed.entity.Feed;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Slice;

public record FeedSliceResponse(
        List<FeedResponse> content,
        Long nextCursor,
        boolean hasNext
) {
    public static FeedSliceResponse from(Slice<Feed> slice, Set<Long> likedFeedIds) {
        List<FeedResponse> content = slice.getContent().stream()
                .map(feed -> FeedResponse.from(feed, likedFeedIds.contains(feed.getId())))
                .toList();

        Long nextCursor = null;
        if (slice.hasNext() && !content.isEmpty()) {
            nextCursor = content.get(content.size() - 1).id();
        }

        return new FeedSliceResponse(content, nextCursor, slice.hasNext());
    }
}
