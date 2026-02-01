package com.shortkki.api.calendar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.calendar.entity.QRecipeQueue;
import com.shortkki.api.calendar.entity.RecipeQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecipeQueueRepositoryImpl implements RecipeQueueRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QRecipeQueue recipeQueue = QRecipeQueue.recipeQueue;

    @Override
    public List<RecipeQueue> findAllByMemberIdWithRecipe(Long memberId) {
        return queryFactory
                .selectFrom(recipeQueue)
                .join(recipeQueue.recipe).fetchJoin()
                .where(recipeQueue.member.id.eq(memberId))
                .orderBy(recipeQueue.createdAt.desc())
                .fetch();
    }
}
