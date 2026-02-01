package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeQueueRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeQueueResponse;
import com.shortkki.api.calendar.entity.RecipeQueue;
import com.shortkki.api.calendar.repository.RecipeQueueRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.service.MemberQueryService;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.service.RecipeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeQueueService {

    private final MemberQueryService memberQueryService;
    private final RecipeQueryService recipeQueryService;
    private final RecipeQueueQueryService recipeQueueQueryService;
    private final RecipeQueueValidationService recipeQueueValidationService;

    private final RecipeQueueRepository recipeQueueRepository;

    public RecipeQueueResponse create(Long memberId, CreateRecipeQueueRequest request) {
        Member member = memberQueryService.findMember(memberId);
        Recipe recipe = recipeQueryService.findRecipe(request.recipeId());

        RecipeQueue queue = RecipeQueue.create(recipe, member);
        recipeQueueRepository.save(queue);

        return RecipeQueueResponse.from(queue);
    }

    public void delete(Long memberId, Long queueId) {
        RecipeQueue queue = recipeQueueQueryService.findRecipeQueue(queueId);
        recipeQueueValidationService.validateRecipeQueueOwner(queue.getId(), memberId);

        recipeQueueRepository.delete(queue);
    }
}
