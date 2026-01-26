package com.shortkki.api.recipe.entity;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_step")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    @Column(name = "step_order")
    private Integer stepOrder;

    @Column(length = 200)
    private String description;

    @Builder
    private RecipeStep(Recipe recipe, Integer stepOrder, String description) {
        this.recipe = recipe;
        this.stepOrder = stepOrder;
        this.description = description;
    }

    public static RecipeStep create(Recipe recipe, Integer stepOrder, String description) {
        return RecipeStep.builder()
                .recipe(recipe)
                .stepOrder(stepOrder)
                .description(description)
                .build();
    }
}
