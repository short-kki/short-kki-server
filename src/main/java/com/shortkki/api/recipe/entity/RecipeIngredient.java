package com.shortkki.api.recipe.entity;

import com.shortkki.api.ingredient.entity.Ingredient;
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
@Table(name = "recipe_ingredient")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeIngredient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Integer amount;

    @Builder
    private RecipeIngredient(Ingredient ingredient, Recipe recipe, Integer amount) {
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.amount = amount;
    }

    public static RecipeIngredient create(Ingredient ingredient, Recipe recipe, Integer amount) {
        return RecipeIngredient.builder()
                .ingredient(ingredient)
                .recipe(recipe)
                .amount(amount)
                .build();
    }

}
