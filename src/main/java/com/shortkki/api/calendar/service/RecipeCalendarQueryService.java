package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.response.GroupRecipeCalendarResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarDetailResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarsResponse;
import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.group.entity.GroupMember;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeCalendarQueryService {

    private final GroupMemberValidationService groupMemberValidationService;

    private final RecipeCalendarRepository recipeCalendarRepository;

    private final GroupMemberRepository groupMemberRepository;

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
        List<RecipeCalendarDetailResponse> groupCalendarDetails = partitioned.get(false);

        Map<Long, List<RecipeCalendarDetailResponse>> groupedByGroupId = groupCalendarDetails.stream()
                .collect(Collectors.groupingBy(RecipeCalendarDetailResponse::groupId));

        List<GroupMember> memberGroups = groupMemberRepository.findAllByMemberIdWithGroup(memberId);

        List<GroupRecipeCalendarResponse> groupCalendars = memberGroups.stream()
                .map(gm -> new GroupRecipeCalendarResponse(
                        gm.getGroup().getId(),
                        gm.getGroup().getName(),
                        groupedByGroupId.getOrDefault(gm.getGroup().getId(), Collections.emptyList())
                ))
                .toList();

        return RecipeCalendarsResponse.builder()
                .personalCalendars(personalCalendars)
                .groupCalendars(groupCalendars)
                .build();
    }
}
