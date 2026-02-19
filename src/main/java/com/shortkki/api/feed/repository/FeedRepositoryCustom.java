package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.group.entity.Group;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FeedRepositoryCustom {

    List<Feed> findAllByGroupWithMember(Group group);

    Optional<Feed> findByIdWithMember(Long feedId);

    Map<Long, LocalDateTime> findLatestCreatedAtByGroupIds(List<Long> groupIds);
}
