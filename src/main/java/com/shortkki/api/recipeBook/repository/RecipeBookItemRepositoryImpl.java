package com.shortkki.api.recipeBook.repository;

import static com.shortkki.api.recipeBook.entity.QRecipeBookItem.recipeBookItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeBookItemRepositoryImpl implements RecipeBookItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByMemberAndRecipeExcludingBook(Long memberId, Long recipeId,
            Long excludeBookId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(recipeBookItem)
                .where(
                        recipeBookItem.recipeBook.member.id.eq(memberId),
                        recipeBookItem.recipe.id.eq(recipeId),
                        recipeBookItem.recipeBook.id.ne(excludeBookId)
                )
                .fetchFirst();
        return fetchOne != null;
    }


    @Override
    public boolean existsByMemberAndRecipe(Long memberId, Long recipeId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(recipeBookItem)
                .where(
                        recipeBookItem.recipeBook.member.id.eq(memberId),
                        recipeBookItem.recipe.id.eq(recipeId)
                )
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
                        recipeBookItem.recipeBook.id.ne(excludeBookId)
                )
                .fetchFirst();
        return fetchOne != null;
    }


    @Override
    public boolean existsByGroupAndRecipe(Long groupId, Long recipeId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(recipeBookItem)
                .where(
                        recipeBookItem.recipeBook.groupId.eq(groupId),
                        recipeBookItem.recipe.id.eq(recipeId)
                )
                .fetchFirst();
        return fetchOne != null;
    }
}
