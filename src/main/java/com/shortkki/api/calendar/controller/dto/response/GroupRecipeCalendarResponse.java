package com.shortkki.api.calendar.controller.dto.response;

import java.util.List;

public record GroupRecipeCalendarResponse(
        Long groupId,
        String groupName,
        List<RecipeCalendarDetailResponse> calendars
) {

}
