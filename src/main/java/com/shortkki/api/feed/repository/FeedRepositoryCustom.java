package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.group.entity.Group;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedRepositoryCustom {

    List<Feed> findAllByGroupWithMember(Group group);

    Slice<Feed> findByGroupWithCursor(Group group, Long cursorId, Pageable pageable);

    Optional<Feed> findByIdWithMember(Long feedId);

    Map<Long, LocalDateTime> findLatestCreatedAtByGroupIds(List<Long> groupIds);
}
