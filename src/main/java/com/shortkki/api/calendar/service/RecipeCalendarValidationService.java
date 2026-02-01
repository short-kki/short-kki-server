package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeCalendarValidationService {

    private final RecipeCalendarQueryService recipeCalendarQueryService;

    public void validateRecipeCalendarOwner(long calendarId, long memberId) {
        RecipeCalendar calendar = recipeCalendarQueryService.getRecipeCalendar(calendarId);
        Member owner = calendar.getMember();

        if (!owner.getId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }
}
