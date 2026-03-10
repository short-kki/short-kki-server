package com.shortkki.api.feed.repository;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.QFeed;
import com.shortkki.api.file.entity.QFileMetadata;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.member.entity.QMember;
import com.shortkki.api.recipe.entity.QRecipe;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
    private final QRecipe recipe = QRecipe.recipe;
    private final QMember recipeAuthor = new QMember("recipeAuthor");
    private final QFileMetadata recipeMainImg = new QFileMetadata("recipeMainImg");
    private final QFileMetadata recipeAuthorProfileImg = new QFileMetadata("recipeAuthorProfileImg");

    @Override
    public List<Feed> findAllByGroupWithMember(Group group) {
        return queryFactory
                .selectFrom(feed)
                .join(feed.member).fetchJoin()
                .leftJoin(feed.member.profileImgFile).fetchJoin()
                .leftJoin(feed.recipe).fetchJoin()
                .where(feed.group.eq(group))
                .orderBy(feed.createdAt.desc())
                .fetch();
    }

    @Override
    public Slice<Feed> findByGroupWithCursor(Group group, Long cursorId, Pageable pageable) {
        List<Feed> results = queryFactory
                .selectFrom(feed)
                .join(feed.member).fetchJoin()
                .leftJoin(feed.member.profileImgFile).fetchJoin()
                .leftJoin(feed.recipe, recipe).fetchJoin()
                .leftJoin(recipe.mainImgFile, recipeMainImg).fetchJoin()
                .leftJoin(recipe.member, recipeAuthor).fetchJoin()
                .leftJoin(recipeAuthor.profileImgFile, recipeAuthorProfileImg).fetchJoin()
                .where(
                        feed.group.eq(group),
                        cursorIdCondition(cursorId)
                )
                .orderBy(feed.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results = results.subList(0, pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }

    private BooleanExpression cursorIdCondition(Long cursorId) {
        return cursorId == null ? null : feed.id.lt(cursorId);
    }

    @Override
    public Optional<Feed> findByIdWithMember(Long feedId) {
        Feed result = queryFactory
                .selectFrom(feed)
                .join(feed.member).fetchJoin()
                .leftJoin(feed.member.profileImgFile).fetchJoin()
                .leftJoin(feed.recipe).fetchJoin()
                .where(feed.id.eq(feedId))
                .fetchOne();
        return java.util.Optional.ofNullable(result);
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
