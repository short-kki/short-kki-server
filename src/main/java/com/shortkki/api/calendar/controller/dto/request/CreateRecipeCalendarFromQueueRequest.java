package com.shortkki.api.calendar.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateRecipeCalendarFromQueueRequest(
        @NotNull(message = "대기열 ID는 필수입니다.")
        Long queueId,

        @NotNull(message = "예정 날짜는 필수입니다.")
        LocalDate scheduledDate,

        Long groupId
) {
}
