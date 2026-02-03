package com.shortkki.api.calendar.controller.dto.response;

import com.shortkki.api.calendar.entity.RecipeCalendar;

import java.time.LocalDate;

public record RecipeCalendarDetailResponse(
        Long id,
        Long recipeId,
        String recipeTitle,
        LocalDate scheduledDate,
        Integer sortOrder,
        Long groupId
) {
    public static RecipeCalendarDetailResponse from(RecipeCalendar calendar) {
        return new RecipeCalendarDetailResponse(
                calendar.getId(),
                calendar.getRecipe().getId(),
                calendar.getRecipe().getBasicInfo().getTitle(),
                calendar.getScheduledDate(),
                calendar.getSortOrder(),
                calendar.getGroup() != null ? calendar.getGroup().getId() : null
        );
    }
}
