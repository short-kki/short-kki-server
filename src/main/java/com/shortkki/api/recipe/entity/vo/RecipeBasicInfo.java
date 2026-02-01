package com.shortkki.api.recipe.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class RecipeBasicInfo {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer servingSize;

    @Column(nullable = false)
    private Integer cookingTime;

    public RecipeBasicInfo(String title, String description, Integer servingSize,
            Integer cookingTime) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다");
        }
        if (servingSize == null || servingSize <= 0) {
            throw new IllegalArgumentException("1인분 기준 양은 양수여야 합니다");
        }
        if (cookingTime == null || cookingTime < 0) {
            throw new IllegalArgumentException("조리 시간은 양수여야 합니다");
        }

        this.title = title;
        this.description = description;
        this.servingSize = servingSize;
        this.cookingTime = cookingTime;
    }
}
