package com.shortkki.api.curation.controller.dto.response;

import com.shortkki.api.curation.entity.Curation;
import com.shortkki.api.curation.entity.DayType;
import com.shortkki.api.curation.entity.TimeType;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;

@Builder
public record CurationResponse(
        Long id,
        boolean active,
        String title,
        String description,
        Set<DayType> dayTypes,
        Set<TimeType> timeTypes,
        Set<CuisineType> cuisineTypes,
        Set<MealType> mealTypes,
        Set<Difficulty> difficulties,
        Set<String> keywords,
        Set<String> tags,
        Set<String> ingredients,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static CurationResponse from(Curation curation) {
        return CurationResponse.builder()
                .id(curation.getId())
                .active(curation.isActive())
                .title(curation.getTitle())
                .description(curation.getDescription())
                .dayTypes(curation.getDayTypes())
                .timeTypes(curation.getTimeTypes())
                .cuisineTypes(curation.getCuisineTypes())
                .mealTypes(curation.getMealTypes())
                .difficulties(curation.getDifficulties())
                .keywords(curation.getKeywords())
                .tags(curation.getTags())
                .ingredients(curation.getIngredients())
                .createdAt(curation.getCreatedAt())
                .updatedAt(curation.getUpdatedAt())
                .build();
    }
}
