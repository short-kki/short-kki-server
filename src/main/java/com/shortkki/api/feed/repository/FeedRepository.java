package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Feed f WHERE f.group = :group")
    void deleteAllByGroup(@Param("group") Group group);
}
