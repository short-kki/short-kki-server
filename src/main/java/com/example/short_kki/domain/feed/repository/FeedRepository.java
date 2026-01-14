package com.example.short_kki.domain.feed.repository;

import com.example.short_kki.domain.feed.entity.Feed;
import com.example.short_kki.domain.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {

    @Query("SELECT f FROM Feed f JOIN FETCH f.member WHERE f.group = :group ORDER BY f.createdAt DESC")
    List<Feed> findAllByGroupOrderByCreatedAtDesc(@Param("group") Group group);
}
