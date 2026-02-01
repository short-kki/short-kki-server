package com.shortkki.api.calendar.entity;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_queue")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeQueue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private RecipeQueue(Recipe recipe, Member member) {
        this.recipe = recipe;
        this.member = member;
    }

    public static RecipeQueue create(Recipe recipe, Member member) {
        return RecipeQueue.builder()
                .recipe(recipe)
                .member(member)
                .build();
    }
}
