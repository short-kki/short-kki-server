package com.shortkki.api.recipeBook.repository;

import static com.shortkki.api.group.entity.QGroupMember.groupMember;
import static com.shortkki.api.recipeBook.entity.QRecipeBookItem.recipeBookItem;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeBookItemRepositoryImpl implements RecipeBookItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findOwnedRecipeBookIdsByRecipeIdAndMemberId(Long recipeId, Long memberId) {
        return queryFactory
                .selectDistinct(recipeBookItem.recipeBook.id)
                .from(recipeBookItem)
                .leftJoin(groupMember)
                .on(recipeBookItem.recipeBook.groupId.eq(groupMember.group.id))
                .where(
                        recipeBookItem.recipe.id.eq(recipeId),
                        recipeBookItem.recipeBook.member.id.eq(memberId)
                                .or(groupMember.member.id.eq(memberId)))
                .fetch();
    }

    @Override
    public boolean existsByMemberAndRecipeExcludingBook(Long memberId, Long recipeId,
            Long excludeBookId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(recipeBookItem)
                .where(
                        recipeBookItem.recipeBook.member.id.eq(memberId),
                        recipeBookItem.recipe.id.eq(recipeId),
                        recipeBookItem.recipeBook.id.ne(excludeBookId))
                .fetchFirst();
        return fetchOne != null;
    }

    @Override
    public boolean existsByGroupAndRecipeExcludingBook(Long groupId, Long recipeId,
            Long excludeBookId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(recipeBookItem)
                .where(
                        recipeBookItem.recipeBook.groupId.eq(groupId),
                        recipeBookItem.recipe.id.eq(recipeId),
                        recipeBookItem.recipeBook.id.ne(excludeBookId))
                .fetchFirst();
        return fetchOne != null;
    }

    @Override
    public List<Long> findRecipeIdsBookmarkedByMemberExcludingBook(Long memberId,
            List<Long> recipeIds,
            Long excludeBookId) {
        return queryFactory
                .selectDistinct(recipeBookItem.recipe.id)
                .from(recipeBookItem)
                .where(
                        recipeBookItem.recipeBook.member.id.eq(memberId),
                        recipeBookItem.recipe.id.in(recipeIds),
                        recipeBookItem.recipeBook.id.ne(excludeBookId))
                .fetch();
    }

    @Override
    public List<Long> findRecipeIdsBookmarkedElsewhere(Long groupId, List<Long> recipeIds) {
        return queryFactory
                .selectDistinct(recipeBookItem.recipe.id)
                .from(recipeBookItem)
                .where(
                        recipeBookItem.recipe.id.in(recipeIds),
                        recipeBookItem.recipeBook.groupId.isNull()
                                .or(recipeBookItem.recipeBook.groupId.ne(groupId)))
                .fetch();
    }

    @Override
    public Map<Long, Long> countByRecipeBookIds(List<Long> recipeBookIds) {
        NumberExpression<Long> countExpr = recipeBookItem.count();
        List<Tuple> rows = queryFactory
                .select(recipeBookItem.recipeBook.id, countExpr)
                .from(recipeBookItem)
                .where(recipeBookItem.recipeBook.id.in(recipeBookIds))
                .groupBy(recipeBookItem.recipeBook.id)
                .fetch();

        return rows.stream()
                .collect(Collectors.toMap(
                        row -> row.get(recipeBookItem.recipeBook.id),
                        row -> row.get(countExpr)));
    }
}
