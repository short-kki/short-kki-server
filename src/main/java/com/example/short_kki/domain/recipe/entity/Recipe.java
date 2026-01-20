package com.example.short_kki.domain.recipe.entity;

import com.example.short_kki.domain.recipe.constant.CuisineType;
import com.example.short_kki.domain.recipe.constant.Difficulty;
import com.example.short_kki.domain.recipe.constant.MealType;
import com.example.short_kki.domain.recipe.constant.RecipeStatus;
import com.example.short_kki.domain.recipe.constant.SourceContentType;
import com.example.short_kki.domain.recipe.constant.SourcePlatform;
import com.example.short_kki.domain.recipe.constant.SourceType;
import com.example.short_kki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer servingSize;

    @Column(nullable = false)
    private Integer cookingTime;

    @ColumnDefault("0")
    private Integer bookmarkCount = 0;

    // -- 카테고리 --
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CuisineType cuisineType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MealType mealType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Difficulty difficulty;

    // -- 출처 --
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private SourceType sourceType;

    @Embedded
    private RecipeSource recipeSource;

    // -- 기타 --
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RecipeStatus status;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Builder
    private Recipe(String title, String description, Integer servingSize, Integer cookingTime,
            CuisineType cuisineType, MealType mealType, Difficulty difficulty,
            SourceType sourceType, RecipeSource recipeSource, RecipeStatus status,
            Integer bookmarkCount, Boolean isDeleted) {
        this.title = title;
        this.description = description;
        this.servingSize = servingSize;
        this.cookingTime = cookingTime;
        this.cuisineType = cuisineType;
        this.mealType = mealType;
        this.difficulty = difficulty;
        this.sourceType = sourceType;
        this.recipeSource = recipeSource;
        this.status = status;
        this.bookmarkCount = bookmarkCount;
        this.isDeleted = isDeleted;
    }

    /**
     * 수동 입력용 정적 팩토리 메서드 출처 정보 없이 사용자가 직접 레시피를 작성할 때 사용
     */
    public static Recipe createManual(String title, String description, Integer servingSize,
            Integer cookingTime, CuisineType cuisineType, MealType mealType,
            Difficulty difficulty) {
        return Recipe.builder()
                .title(title)
                .description(description)
                .servingSize(servingSize)
                .cookingTime(cookingTime)
                .cuisineType(cuisineType)
                .mealType(mealType)
                .difficulty(difficulty)
                .sourceType(SourceType.USER_CREATED)
                .status(RecipeStatus.DRAFT)
                .build();
    }

    /**
     * 링크 가져오기용 정적 팩토리 메서드 외부 URL에서 레시피를 파싱해서 가져올 때 사용
     */
    public static Recipe createFromLink(String title, String description, Integer servingSize,
            Integer cookingTime, CuisineType cuisineType, MealType mealType, Difficulty difficulty,
            RecipeSource recipeSource) {
        return Recipe.builder()
                .title(title)
                .description(description)
                .servingSize(servingSize)
                .cookingTime(cookingTime)
                .cuisineType(cuisineType)
                .mealType(mealType)
                .difficulty(difficulty)
                .sourceType(SourceType.IMPORTED)
                .recipeSource(recipeSource)
                .status(RecipeStatus.DRAFT)
                .build();
    }

    // --- 편의 메서드: RecipeSource 필드 접근 (null-safe) ---

    public String getSourceUrl() {
        return recipeSource != null ? recipeSource.getUrl() : null;
    }

    public SourcePlatform getSourcePlatform() {
        return recipeSource != null ? recipeSource.getPlatform() : null;
    }

    public SourceContentType getSourceContentType() {
        return recipeSource != null ? recipeSource.getContentType() : null;
    }
}
