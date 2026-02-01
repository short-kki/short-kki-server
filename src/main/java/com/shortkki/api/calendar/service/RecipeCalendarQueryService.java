package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeCalendarQueryService {

    private final RecipeCalendarRepository recipeCalendarRepository;

    public RecipeCalendar getRecipeCalendar(long id) {
        return recipeCalendarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_CALENDAR_NOT_FOUND));
    }
}
