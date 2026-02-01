package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarDetailResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarsResponse;
import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.api.group.service.GroupMemberValidationService;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeCalendarQueryService {

    private final GroupMemberValidationService groupMemberValidationService;

    private final RecipeCalendarRepository recipeCalendarRepository;

    public RecipeCalendar findRecipeCalendar(long id) {
        return recipeCalendarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_CALENDAR_NOT_FOUND));
    }

    // TODO: 페이지네이션
    public RecipeCalendarsResponse getRecipeCalendars(
            Long memberId, Long groupId, LocalDate startDate, LocalDate endDate
    ) {
        if (groupId != null) {
            groupMemberValidationService.validateGroupMember(memberId, groupId);
        }

        List<RecipeCalendarDetailResponse> recipeCalendars = recipeCalendarRepository
                .findAllByMemberAndDateRange(memberId, groupId, startDate, endDate)
                .stream()
                .map(RecipeCalendarDetailResponse::from)
                .toList();

        return RecipeCalendarsResponse.builder()
                .recipeCalendars(recipeCalendars)
                .build();
    }
}
