package com.shortkki.api.calendar.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.calendar.entity.QRecipeCalendar;
import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.group.entity.QGroupMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecipeCalendarRepositoryImpl implements RecipeCalendarRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QRecipeCalendar recipeCalendar = QRecipeCalendar.recipeCalendar;

    @Override
    public List<RecipeCalendar> findAllByMemberAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .selectFrom(recipeCalendar)
                .join(recipeCalendar.recipe).fetchJoin()
                .leftJoin(recipeCalendar.recipe.sourceContent).fetchJoin()
                .leftJoin(recipeCalendar.recipe.mainImgFile).fetchJoin()
                .leftJoin(recipeCalendar.group).fetchJoin()
                .where(
                        memberOrGroupCondition(memberId),
                        recipeCalendar.scheduledDate.between(startDate, endDate)
                )
                .orderBy(recipeCalendar.scheduledDate.asc(), recipeCalendar.sortOrder.asc())
                .fetch();
    }

    @Override
    public List<RecipeCalendar> findAllByOwnerAndDate(Long memberId, Long groupId, LocalDate scheduledDate) {
        return queryFactory
                .selectFrom(recipeCalendar)
                .where(
                        singleOwnerCondition(memberId, groupId),
                        recipeCalendar.scheduledDate.eq(scheduledDate)
                )
                .orderBy(recipeCalendar.sortOrder.asc())
                .fetch();
    }

    private BooleanExpression singleOwnerCondition(Long memberId, Long groupId) {
        if (groupId != null) {
            return recipeCalendar.group.id.eq(groupId);
        }
        return recipeCalendar.member.id.eq(memberId)
                .and(recipeCalendar.group.isNull());
    }

    private BooleanExpression memberOrGroupCondition(Long memberId) {
        QGroupMember groupMember = QGroupMember.groupMember;

        BooleanExpression personalCondition = recipeCalendar.member.id.eq(memberId);
        BooleanExpression groupCondition = recipeCalendar.group.id.in(
                JPAExpressions
                        .select(groupMember.group.id)
                        .from(groupMember)
                        .where(groupMember.member.id.eq(memberId))
        );

        return personalCondition.or(groupCondition);
    }
}
