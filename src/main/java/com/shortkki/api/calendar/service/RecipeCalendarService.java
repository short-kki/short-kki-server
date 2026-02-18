package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeCalendarFromQueueRequest;
import com.shortkki.api.calendar.controller.dto.request.ReorderRecipeCalendarRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarDetailResponse;
import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.calendar.entity.RecipeQueue;
import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.api.calendar.repository.RecipeQueueRepository;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.group.application.service.GroupQueryService;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.service.MemberQueryService;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeCalendarService {

    private final RecipeQueueQueryService recipeQueueQueryService;
    private final RecipeCalendarQueryService recipeCalendarQueryService;
    private final GroupQueryService groupQueryService;

    private final RecipeQueueValidationService recipeQueueValidationService;
    private final RecipeCalendarValidationService recipeCalendarValidationService;
    private final GroupMemberValidationService groupMemberValidationService;

    private final RecipeCalendarRepository recipeCalendarRepository;
    private final RecipeQueueRepository recipeQueueRepository;
    private final MemberQueryService memberQueryService;

    // TODO: 생성 개수 제한 두기
    public RecipeCalendarDetailResponse createFromQueue(Long memberId, CreateRecipeCalendarFromQueueRequest request) {
        RecipeQueue queue = recipeQueueQueryService.findRecipeQueue(request.queueId());
        recipeQueueValidationService.validateRecipeQueueOwner(queue.getId(), memberId);
        Member member = memberQueryService.findMember(memberId);

        RecipeCalendar calendar = request.groupId() == null
                ? createMemberCalendar(queue, member, request.scheduledDate())
                : createGroupCalendar(queue, member, request.groupId(), request.scheduledDate());

        recipeCalendarRepository.save(calendar);
        recipeQueueRepository.delete(queue);

        return RecipeCalendarDetailResponse.from(calendar);
    }

    public void deleteCalendar(Long memberId, Long calendarId) {
        RecipeCalendar calendar = recipeCalendarQueryService.findRecipeCalendar(calendarId);
        recipeCalendarValidationService.validateDeleteAccess(calendar, memberId);
        recipeCalendarRepository.delete(calendar);
    }

    public void reorder(Long memberId, ReorderRecipeCalendarRequest request) {
        if (request.groupId() != null) {
            groupMemberValidationService.validateGroupMember(memberId, request.groupId());
        }

        List<RecipeCalendar> calendars = recipeCalendarRepository
                .findAllByOwnerAndDate(memberId, request.groupId(), request.scheduledDate());

        validateReorderRequest(request.calendarIds(), calendars);

        Map<Long, RecipeCalendar> calendarMap = calendars.stream()
                .collect(Collectors.toMap(RecipeCalendar::getId, c -> c));

        for (int i = 0; i < request.calendarIds().size(); i++) {
            RecipeCalendar calendar = calendarMap.get(request.calendarIds().get(i));
            calendar.updateSortOrder(i + 1);
        }

        recipeCalendarRepository.saveAll(calendarMap.values());
    }

    private RecipeCalendar createMemberCalendar(
            RecipeQueue queue, Member member, LocalDate scheduledDate
    ) {
        int maxOrder = recipeCalendarRepository.findPersonalMaxSortOrder(member.getId(), scheduledDate);
        int sortOrder = maxOrder + 1;

        return RecipeCalendar.createForMember(
                queue.getRecipe(), member, scheduledDate, sortOrder
        );
    }

    private RecipeCalendar createGroupCalendar(
            RecipeQueue queue, Member requester, long groupId, LocalDate scheduledDate
    ) {
        Group group = groupQueryService.findGroup(groupId);
        groupMemberValidationService.validateGroupMember(requester.getId(), groupId);

        int maxOrder = recipeCalendarRepository.findGroupMaxSortOrder(group.getId(), scheduledDate);
        int sortOrder = maxOrder + 1;

        return RecipeCalendar.createForGroup(
                queue.getRecipe(), group, scheduledDate, sortOrder
        );
    }

    private void validateReorderRequest(List<Long> requestIds, List<RecipeCalendar> calendars) {
        if (requestIds.size() != calendars.size()) {
            throw new BadRequestException(ErrorCode.INVALID_CALENDAR_REORDER_REQUEST);
        }

        Set<Long> requestIdSet = new HashSet<>(requestIds);
        Set<Long> calendarIdSet = calendars.stream()
                .map(RecipeCalendar::getId)
                .collect(Collectors.toSet());

        if (!requestIdSet.equals(calendarIdSet)) {
            throw new BadRequestException(ErrorCode.INVALID_CALENDAR_REORDER_REQUEST);
        }
    }
}
