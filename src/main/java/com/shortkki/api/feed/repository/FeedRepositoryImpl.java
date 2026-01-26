package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.QFeed;
import com.shortkki.api.group.entity.Group;
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
