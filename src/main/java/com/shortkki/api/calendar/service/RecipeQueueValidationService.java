package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.entity.RecipeQueue;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeQueueValidationService {

    private final RecipeQueueQueryService recipeQueueQueryService;

    public void validateRecipeQueueOwner(long queueId, long memberId) {
        RecipeQueue queue = recipeQueueQueryService.findRecipeQueue(queueId);
        Member owner = queue.getMember();

        if (!owner.getId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }
}
