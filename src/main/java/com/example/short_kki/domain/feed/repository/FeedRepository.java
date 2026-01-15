package com.example.short_kki.domain.feed.repository;

import com.example.short_kki.domain.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {

}
