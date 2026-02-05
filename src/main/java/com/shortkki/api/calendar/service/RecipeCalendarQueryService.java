package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarDetailResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarsResponse;
import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.Map;
import java.util.stream.Collectors;
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
            Long memberId, LocalDate startDate, LocalDate endDate
    ) {
        List<RecipeCalendarDetailResponse> calendarDetails = recipeCalendarRepository
                .findAllByMemberAndDateRange(memberId, startDate, endDate)
                .stream()
                .map(RecipeCalendarDetailResponse::from)
                .toList();

        Map<Boolean, List<RecipeCalendarDetailResponse>> partitioned = calendarDetails.stream()
                .collect(Collectors.partitioningBy(
                        detail -> detail.groupId() == null
                ));

        List<RecipeCalendarDetailResponse> personalCalendars = partitioned.get(true);
        List<RecipeCalendarDetailResponse> groupCalendars = partitioned.get(false);

        return RecipeCalendarsResponse.builder()
                .personals(personalCalendars)
                .groups(groupCalendars)
                .build();
    }
}
