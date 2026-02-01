package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeCalendarFromQueueRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarResponse;
import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.calendar.entity.RecipeQueue;
import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.api.calendar.repository.RecipeQueueRepository;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.service.GroupQueryService;
import com.shortkki.api.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeCalendarService {

    private final RecipeQueueQueryService recipeQueueQueryService;
    private final RecipeQueueValidationService recipeQueueValidationService;
    private final RecipeCalendarQueryService recipeCalendarQueryService;
    private final RecipeCalendarValidationService recipeCalendarValidationService;
    private final GroupQueryService groupQueryService;

    private final RecipeCalendarRepository recipeCalendarRepository;
    private final RecipeQueueRepository recipeQueueRepository;

    public RecipeCalendarResponse createFromQueue(Long memberId, CreateRecipeCalendarFromQueueRequest request) {
        RecipeQueue queue = recipeQueueQueryService.findRecipeQueue(request.queueId());
        recipeQueueValidationService.validateRecipeQueueOwner(queue.getId(), memberId);

        Group group = null;
        if (request.groupId() != null) {
            group = groupQueryService.findGroup(request.groupId());
        }

        Member member = queue.getMember();
        int sortOrder = recipeCalendarRepository.findMaxSortOrder(member, request.scheduledDate()) + 1;

        RecipeCalendar calendar = RecipeCalendar.create(
                queue.getRecipe(), member, group, request.scheduledDate(), sortOrder
        );
        recipeCalendarRepository.save(calendar);
        recipeQueueRepository.delete(queue);

        return RecipeCalendarResponse.from(calendar);
    }

    public void delete(Long memberId, Long calendarId) {
        RecipeCalendar calendar = recipeCalendarQueryService.findRecipeCalendar(calendarId);
        recipeCalendarValidationService.validateRecipeCalendarOwner(calendarId, memberId);

        recipeCalendarRepository.delete(calendar);
    }
}
