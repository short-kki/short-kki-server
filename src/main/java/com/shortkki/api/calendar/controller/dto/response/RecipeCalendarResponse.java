package com.shortkki.api.calendar.controller.dto.response;

import com.shortkki.api.calendar.entity.RecipeCalendar;

import java.time.LocalDate;

public record RecipeCalendarResponse(
        Long id,
        Long recipeId,
        String recipeTitle,
        LocalDate scheduledDate,
        Integer sortOrder,
        Long groupId
) {
    public static RecipeCalendarResponse from(RecipeCalendar calendar) {
        return new RecipeCalendarResponse(
                calendar.getId(),
                calendar.getRecipe().getId(),
                calendar.getRecipe().getTitle(),
                calendar.getScheduledDate(),
                calendar.getSortOrder(),
                calendar.getGroup() != null ? calendar.getGroup().getId() : null
        );
    }
}
