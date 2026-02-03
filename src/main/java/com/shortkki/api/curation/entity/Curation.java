package com.shortkki.api.curation.entity;

import com.shortkki.api.curation.entity.converter.CuisineTypeSetConverter;
import com.shortkki.api.curation.entity.converter.DayTypeSetConverter;
import com.shortkki.api.curation.entity.converter.DifficultySetConverter;
import com.shortkki.api.curation.entity.converter.MealTypeSetConverter;
import com.shortkki.api.curation.entity.converter.StringSetConverter;
import com.shortkki.api.curation.entity.converter.TimeTypeSetConverter;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "curation")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Curation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Convert(converter = DayTypeSetConverter.class)
    @Column(length = 500, nullable = false)
    private Set<DayType> dayTypes;

    @Convert(converter = TimeTypeSetConverter.class)
    @Column(length = 500, nullable = false)
    private Set<TimeType> timeTypes;

    @Convert(converter = CuisineTypeSetConverter.class)
    @Column(length = 1000)
    private Set<CuisineType> cuisineTypes;

    @Convert(converter = MealTypeSetConverter.class)
    @Column(length = 1000)
    private Set<MealType> mealTypes;

    @Convert(converter = DifficultySetConverter.class)
    @Column(length = 1000)
    private Set<Difficulty> difficulties;

    @Convert(converter = StringSetConverter.class)
    @Column(length = 2000)
    private Set<String> keywords;

    @Convert(converter = StringSetConverter.class)
    @Column(length = 2000)
    private Set<String> tags;

    @Convert(converter = StringSetConverter.class)
    @Column(length = 2000)
    private Set<String> ingredients;

    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;

    public static Curation create(
            String title,
            String description,
            Set<DayType> dayTypes,
            Set<TimeType> timeTypes,
            Set<CuisineType> cuisineTypes,
            Set<MealType> mealTypes,
            Set<Difficulty> difficulties,
            Set<String> keywords,
            Set<String> tags,
            Set<String> ingredients
    ) {
        validateTitle(title);
        validateDayTime(dayTypes, timeTypes);

        return Curation.builder()
                .title(title)
                .description(description)
                .dayTypes(dayTypes)
                .timeTypes(timeTypes)
                .cuisineTypes(cuisineTypes)
                .mealTypes(mealTypes)
                .difficulties(difficulties)
                .keywords(keywords)
                .tags(tags)
                .ingredients(ingredients)
                .build();
    }

    private static void validateDayTime(Set<DayType> dayTypes, Set<TimeType> timeTypes) {
        if (dayTypes == null || dayTypes.isEmpty() || timeTypes == null || timeTypes.isEmpty()) {
            throw new IllegalArgumentException("요일 타입과 시간대 타입은 비어있을 수 없습니다.");
        }
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("큐레이션 제목은 필수입니다.");
        }
    }
}
