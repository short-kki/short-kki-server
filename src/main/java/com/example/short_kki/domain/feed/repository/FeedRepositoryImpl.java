package com.example.short_kki.domain.feed.repository;

import com.example.short_kki.domain.feed.entity.Feed;
import com.example.short_kki.domain.feed.entity.QFeed;
import com.example.short_kki.domain.group.entity.Group;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QFeed feed = QFeed.feed;

    @Override
    public List<Feed> findAllByGroupWithMember(Group group) {
        return queryFactory
                .selectFrom(feed)
                .join(feed.member).fetchJoin()
                .where(feed.group.eq(group))
                .orderBy(feed.createdAt.desc())
                .fetch();
    }
}
