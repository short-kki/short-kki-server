package com.shortkki.api.calendar.controller.dto.response;

import com.shortkki.api.calendar.entity.RecipeCalendar;

import java.time.LocalDate;

public record RecipeCalendarDetailResponse(
        Long id,
        Long recipeId,
        String recipeTitle,
        Integer cookingTime,
        String mainImgUrl,
        LocalDate scheduledDate,
        Integer sortOrder,
        Long groupId,
        String groupName
) {
    public static RecipeCalendarDetailResponse from(RecipeCalendar calendar) {
        return new RecipeCalendarDetailResponse(
                calendar.getId(),
                calendar.getRecipe().getId(),
                calendar.getRecipe().getBasicInfo().getTitle(),
                calendar.getRecipe().getBasicInfo().getCookingTime(),
                calendar.getRecipe().getMainImgUrl(),
                calendar.getScheduledDate(),
                calendar.getSortOrder(),
                calendar.getGroup() != null ? calendar.getGroup().getId() : null,
                calendar.getGroup() != null ? calendar.getGroup().getName() : null
        );
    }
}
