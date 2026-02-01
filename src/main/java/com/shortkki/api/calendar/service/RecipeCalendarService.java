package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeCalendarFromQueueRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarDetailResponse;
import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.calendar.entity.RecipeQueue;
import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.api.calendar.repository.RecipeQueueRepository;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.service.GroupMemberValidationService;
import com.shortkki.api.group.service.GroupQueryService;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.service.MemberQueryService;
import java.time.LocalDate;
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

    public void delete(Long memberId, Long calendarId) {
        RecipeCalendar calendar = recipeCalendarQueryService.findRecipeCalendar(calendarId);
        recipeCalendarValidationService.validateRecipeCalendarOwner(calendarId, memberId);

        recipeCalendarRepository.delete(calendar);
    }
}
