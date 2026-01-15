package com.example.short_kki.domain.feed.repository;

import com.example.short_kki.domain.feed.entity.Feed;
import com.example.short_kki.domain.group.entity.Group;

import java.util.List;

public interface FeedRepositoryCustom {

    List<Feed> findAllByGroupWithMember(Group group);
}
