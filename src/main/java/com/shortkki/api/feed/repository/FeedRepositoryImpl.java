package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.QFeed;
import com.shortkki.api.group.entity.Group;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .leftJoin(feed.recipe).fetchJoin()
                .where(feed.group.eq(group))
                .orderBy(feed.createdAt.desc())
                .fetch();
    }

    @Override
    public Map<Long, LocalDateTime> findLatestCreatedAtByGroupIds(List<Long> groupIds) {
        List<Tuple> results = queryFactory
                .select(feed.group.id, feed.createdAt.max())
                .from(feed)
                .where(feed.group.id.in(groupIds))
                .groupBy(feed.group.id)
                .fetch();

        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(feed.group.id),
                        tuple -> tuple.get(feed.createdAt.max())
                ));
    }
}
