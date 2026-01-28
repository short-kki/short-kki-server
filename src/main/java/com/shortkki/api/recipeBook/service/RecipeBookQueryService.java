package com.shortkki.api.recipeBook.service;

import static com.shortkki.api.recipeBook.entity.QRecipeBook.recipeBook;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeBookQueryService {

    private final JPAQueryFactory queryFactory;

    public List<RecipeBook> findAllByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(recipeBook)
                .where(recipeBook.member.id.eq(memberId))
                .orderBy(recipeBook.sortOrder.asc())
                .fetch();
    }

    public List<RecipeBook> findAllByGroupId(Long groupId) {
        return queryFactory
                .selectFrom(recipeBook)
                .where(recipeBook.groupId.eq(groupId))
                .orderBy(recipeBook.sortOrder.asc())
                .fetch();
    }

    public List<RecipeBook> searchRecipeBooks(Long memberId, Long groupId, String title,
            Boolean isDefault) {

        BooleanBuilder builder = new BooleanBuilder();

        if (memberId != null) {
            builder.and(recipeBook.member.id.eq(memberId));
        }

        if (groupId != null) {
            builder.and(recipeBook.groupId.eq(groupId));
        }

        if (title != null && !title.isBlank()) {
            builder.and(recipeBook.title.containsIgnoreCase(title));
        }
        
        if (isDefault != null) {
            builder.and(recipeBook.isDefault.eq(isDefault));
        }

        return queryFactory
                .selectFrom(recipeBook)
                .where(builder)
                .orderBy(recipeBook.sortOrder.asc())
                .fetch();
    }

    public RecipeBook findDefaultByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(recipeBook)
                .where(
                        recipeBook.member.id.eq(memberId),
                        recipeBook.isDefault.eq(true))
                .fetchOne();
    }

    public RecipeBook findDefaultByGroupId(Long groupId) {
        return queryFactory
                .selectFrom(recipeBook)
                .where(
                        recipeBook.groupId.eq(groupId),
                        recipeBook.isDefault.eq(true))
                .fetchOne();
    }

    public long countByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(recipeBook)
                .where(recipeBook.member.id.eq(memberId))
                .stream()
                .count();
    }

    public long countByGroupId(Long groupId) {
        return queryFactory
                .selectFrom(recipeBook)
                .where(recipeBook.groupId.eq(groupId))
                .stream()
                .count();
    }
}
