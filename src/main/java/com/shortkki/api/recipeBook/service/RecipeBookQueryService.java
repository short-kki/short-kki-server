package com.shortkki.api.recipeBook.service;

import static com.shortkki.api.recipeBook.entity.QRecipeBook.recipeBook;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import java.util.List;
import java.util.Optional;
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
                .orderBy(recipeBook.title.asc())
                .fetch();
    }

    public Optional<RecipeBook> findDefaultByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(recipeBook)
                        .where(
                                recipeBook.member.id.eq(memberId),
                                recipeBook.isDefault.isTrue())
                        .fetchOne());
    }
}
