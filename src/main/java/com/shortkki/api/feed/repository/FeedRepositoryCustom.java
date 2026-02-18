package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.group.entity.Group;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface FeedRepositoryCustom {

    List<Feed> findAllByGroupWithMember(Group group);

    Map<Long, LocalDateTime> findLatestCreatedAtByGroupIds(List<Long> groupIds);
}
