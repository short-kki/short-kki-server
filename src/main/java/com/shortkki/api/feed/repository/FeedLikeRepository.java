package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.FeedLike;
import com.shortkki.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    boolean existsByFeedAndMember(Feed feed, Member member);

    @Query("SELECT COUNT(fl) > 0 FROM FeedLike fl WHERE fl.feed = :feed AND fl.member.id = :memberId")
    boolean existsByFeedAndMemberId(@Param("feed") Feed feed, @Param("memberId") Long memberId);

    Optional<FeedLike> findByFeedAndMember(Feed feed, Member member);

    long countByFeed(Feed feed);

    @Query("SELECT fl.feed.id FROM FeedLike fl WHERE fl.member.id = :memberId AND fl.feed IN :feeds")
    Set<Long> findLikedFeedIdsByMemberIdAndFeedIn(@Param("memberId") Long memberId, @Param("feeds") List<Feed> feeds);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM FeedLike fl WHERE fl.feed = :feed")
    void deleteByFeed(@Param("feed") Feed feed);
}
