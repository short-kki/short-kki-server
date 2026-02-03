package com.shortkki.api.feed.entity;

import com.shortkki.api.member.entity.Member;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"feed_id", "member_id"})
})
public class FeedLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private FeedLike(Feed feed, Member member) {
        this.feed = feed;
        this.member = member;
    }

    public static FeedLike create(Feed feed, Member member) {
        return FeedLike.builder()
                .feed(feed)
                .member(member)
                .build();
    }
}
