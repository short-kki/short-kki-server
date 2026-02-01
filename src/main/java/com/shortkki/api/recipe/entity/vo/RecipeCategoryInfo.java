package com.shortkki.api.recipe.entity.vo;

import com.shortkki.api.recipe.constant.CuisineType;
import com.shortkki.api.recipe.constant.Difficulty;
import com.shortkki.api.recipe.constant.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class RecipeCategoryInfo {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CuisineType cuisineType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MealType mealType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Difficulty difficulty;

    public RecipeCategoryInfo(CuisineType cuisineType, MealType mealType, Difficulty difficulty) {
        Assert.notNull(cuisineType, "CuisineType must not be null");
        Assert.notNull(mealType, "MealType must not be null");
        Assert.notNull(difficulty, "Difficulty must not be null");

        this.cuisineType = cuisineType;
        this.mealType = mealType;
        this.difficulty = difficulty;
    }
}
