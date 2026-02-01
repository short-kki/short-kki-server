package com.shortkki.api.calendar.repository;

import com.shortkki.api.calendar.entity.RecipeCalendar;

import java.time.LocalDate;
import java.util.List;

public interface RecipeCalendarRepositoryCustom {

    List<RecipeCalendar> findAllByMemberAndDateRange(
            Long memberId, Long groupId, LocalDate startDate, LocalDate endDate
    );
}
