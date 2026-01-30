package com.shortkki.api.recipe.entity;

import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.recipe.dto.request.BasicInfoRequest;
import com.shortkki.api.recipe.dto.request.CategoryInfoRequest;
import com.shortkki.global.entity.BaseEntity;
import com.shortkki.global.error.exception.InvalidStateException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "recipe")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeIngredient> ingredients;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_content_id")
    private SourceContent sourceContent;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer servingSize;

    @Column(nullable = false)
    private Integer cookingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CuisineType cuisineType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MealType mealType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", length = 50)
    private RecipeSource sourceType;

    @Builder.Default
    @ColumnDefault("0")
    private Integer bookmarkCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = false;

    public static Recipe createManual(
            String title, String description, Integer servingSize, Integer cookingTime,
            CuisineType cuisineType, MealType mealType, Difficulty difficulty
    ) {
        return Recipe.builder()
                .title(title)
                .description(description)
                .servingSize(servingSize)
                .cookingTime(cookingTime)
                .cuisineType(cuisineType)
                .mealType(mealType)
                .difficulty(difficulty)
                .sourceType(RecipeSource.USER)
                .build();
    }

    public static Recipe createImported(
            String title, String description, Integer servingSize, Integer cookingTime,
            CuisineType cuisineType, MealType mealType, Difficulty difficulty,
            SourceContent sourceContent
    ) {
        if (sourceContent == null) {
            throw new InvalidStateException("원본 컨텐츠 정보 없이 외부 레시피를 생성할 수 없습니다.");
        }

        return Recipe.builder()
                .title(title)
                .description(description)
                .servingSize(servingSize)
                .cookingTime(cookingTime)
                .cuisineType(cuisineType)
                .mealType(mealType)
                .difficulty(difficulty)
                .sourceType(RecipeSource.IMPORT)
                .sourceContent(sourceContent)
                .bookmarkCount(0)
                .isDeleted(false)
                .build();
    }

    public void update(
            BasicInfoRequest basicInfo, CategoryInfoRequest categoryInfo
    ) {
        this.title = basicInfo.title();
        this.description = basicInfo.description();
        this.servingSize = basicInfo.servingSize();
        this.cookingTime = basicInfo.cookingTime();
        this.cuisineType = categoryInfo.cuisineType();
        this.mealType = categoryInfo.mealType();
        this.difficulty = categoryInfo.difficulty();
    }

    public String getSourceUrl() {
        return RecipeSource.IMPORT == sourceType ? sourceContent.getCanonicalUrl() : null;
    }

    public SourcePlatform getSourcePlatform() {
        return RecipeSource.IMPORT == sourceType ? sourceContent.getPlatform() : null;
    }

    public SourceContentType getSourceContentType() {
        return RecipeSource.IMPORT == sourceType ? sourceContent.getContentType() : null;
    }
}
