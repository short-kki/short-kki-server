package com.shortkki.api.calendar.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ReorderRecipeCalendarRequest(
        @NotNull(message = "날짜는 필수입니다") LocalDate scheduledDate,
        Long groupId,
        @NotEmpty(message = "레시피 캘린더 ID 목록은 비어있을 수 없습니다") List<Long> calendarIds
) {
}
