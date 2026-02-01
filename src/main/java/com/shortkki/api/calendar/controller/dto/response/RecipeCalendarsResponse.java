package com.shortkki.api.calendar.controller.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record RecipeCalendarsResponse(
        List<RecipeCalendarDetailResponse> recipeCalendars
) {

}
