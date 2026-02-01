package com.shortkki.api.calendar.entity;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "recipe_calendar")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeCalendar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private Integer sortOrder;

    public static RecipeCalendar createForMember(
            Recipe recipe, Member member, LocalDate scheduledDate, int sortOrder
    ) {
        return RecipeCalendar.builder()
                .recipe(recipe)
                .member(member)
                .scheduledDate(scheduledDate)
                .sortOrder(sortOrder)
                .build();
    }

    public static RecipeCalendar createForGroup(
            Recipe recipe, Group group, LocalDate scheduledDate, Integer sortOrder
    ) {
        return RecipeCalendar.builder()
                .recipe(recipe)
                .group(group)
                .scheduledDate(scheduledDate)
                .sortOrder(sortOrder)
                .build();
    }

    public void updateSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
