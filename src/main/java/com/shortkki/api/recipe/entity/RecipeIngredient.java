package com.shortkki.api.recipe.entity;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.global.entity.BaseEntity;
import com.shortkki.global.error.exception.InvalidStateException;
import io.micrometer.core.annotation.Counted;
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

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 50)
    private String unit;

    @Builder
    private RecipeIngredient(Ingredient ingredient, Recipe recipe, String name, Double amount,
            String unit
    ) {
        validate(name, unit, amount);
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public static RecipeIngredient create(Ingredient ingredient, Recipe recipe, String name,
            Double amount,
            String unit
    ) {
        return RecipeIngredient.builder()
                .ingredient(ingredient)
                .recipe(recipe)
                .name(name)
                .amount(amount)
                .unit(unit)
                .build();
    }

    private void validate(String name, String unit, Double amount) {
        if (name == null || name.isBlank()) {
            throw new InvalidStateException("재료명은 필수입니다");
        }
        if (unit == null || unit.isBlank()) {
            throw new InvalidStateException("단위는 필수입니다");
        }
        if (amount == null || amount < 0) {
            throw new InvalidStateException("수량은 0 이상이어야 합니다");
        }
    }

}
