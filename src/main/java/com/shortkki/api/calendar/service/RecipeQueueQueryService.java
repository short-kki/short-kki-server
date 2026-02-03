package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.response.RecipeQueueDetailResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeQueuesResponse;
import com.shortkki.api.calendar.entity.RecipeQueue;
import com.shortkki.api.calendar.repository.RecipeQueueRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeQueueQueryService {

    private final RecipeQueueRepository recipeQueueRepository;

    public RecipeQueue findRecipeQueue(long id) {
        return recipeQueueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_QUEUE_NOT_FOUND));
    }

    public RecipeQueuesResponse getRecipeQueues(Long memberId) {
        List<RecipeQueueDetailResponse> recipeQueues = recipeQueueRepository
                .findAllByMemberIdWithRecipe(memberId)
                .stream()
                .map(RecipeQueueDetailResponse::from)
                .toList();

        return RecipeQueuesResponse.builder()
                .recipeQueues(recipeQueues)
                .build();
    }
}
