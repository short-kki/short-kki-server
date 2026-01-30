package com.shortkki.api.recipe.entity;

import com.shortkki.api.recipe.constant.CuisineType;
import com.shortkki.api.recipe.constant.Difficulty;
import com.shortkki.api.recipe.constant.MealType;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.recipe.constant.SourceType;
import com.shortkki.api.recipe.dto.BasicInfoRequest;
import com.shortkki.api.recipe.dto.CategoryInfoRequest;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.entity.BaseEntity;
import com.shortkki.global.error.exception.InvalidStateException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    @ColumnDefault("0")
    private Integer bookmarkCount = 0;

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
    @Column(length = 50)
    private SourceType sourceType;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Builder
    private Recipe(
            Member member,
            String title, String description, Integer servingSize, Integer cookingTime,
            CuisineType cuisineType, MealType mealType, Difficulty difficulty,
            Integer bookmarkCount,
            SourceType sourceType, SourceContent sourceContent, Boolean isDeleted
    ) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.servingSize = servingSize;
        this.cookingTime = cookingTime;
        this.cuisineType = cuisineType;
        this.mealType = mealType;
        this.difficulty = difficulty;
        this.sourceType = sourceType;
        this.sourceContent = sourceContent;
        this.bookmarkCount = bookmarkCount;
        this.isDeleted = isDeleted;
    }

    public static Recipe createManual(
            Member member,
            String title, String description, Integer servingSize, Integer cookingTime,
            CuisineType cuisineType, MealType mealType, Difficulty difficulty
    ) {
        return Recipe.builder()
                .member(member)
                .title(title)
                .description(description)
                .servingSize(servingSize)
                .cookingTime(cookingTime)
                .cuisineType(cuisineType)
                .mealType(mealType)
                .difficulty(difficulty)
                .sourceType(SourceType.USER_CREATED)
                .build();
    }

    public static Recipe createImported(
            Member member,
            String title, String description, Integer servingSize, Integer cookingTime,
            CuisineType cuisineType, MealType mealType, Difficulty difficulty,
            SourceContent sourceContent) {
        if (sourceContent == null) {
            throw new InvalidStateException("원본 컨텐츠 정보 없이 외부 레시피를 생성할 수 없습니다.");
        }

        return Recipe.builder()
                .member(member)
                .title(title)
                .description(description)
                .servingSize(servingSize)
                .cookingTime(cookingTime)
                .cuisineType(cuisineType)
                .mealType(mealType)
                .difficulty(difficulty)
                .sourceType(SourceType.IMPORTED)
                .isDeleted(false)
                .bookmarkCount(0)
                .sourceContent(sourceContent)
                .build();
    }

    public void update(
            BasicInfoRequest basicInfo, CategoryInfoRequest categoryInfo) {
        this.title = basicInfo.title();
        this.description = basicInfo.description();
        this.servingSize = basicInfo.servingSize();
        this.cookingTime = basicInfo.cookingTime();
        this.cuisineType = categoryInfo.cuisineType();
        this.mealType = categoryInfo.mealType();
        this.difficulty = categoryInfo.difficulty();
    }

    public String getSourceUrl() {
        return SourceType.IMPORTED == sourceType ? sourceContent.getCanonicalUrl() : null;
    }

    public SourcePlatform getSourcePlatform() {
        return SourceType.IMPORTED == sourceType ? sourceContent.getPlatform() : null;
    }

    public SourceContentType getSourceContentType() {
        return SourceType.IMPORTED == sourceType ? sourceContent.getContentType() : null;
    }
}
