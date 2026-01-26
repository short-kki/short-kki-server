package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.group.entity.Group;

import java.util.List;

public interface FeedRepositoryCustom {

    List<Feed> findAllByGroupWithMember(Group group);
}
