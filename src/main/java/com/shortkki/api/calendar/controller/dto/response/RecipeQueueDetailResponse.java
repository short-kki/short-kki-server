package com.shortkki.api.calendar.controller.dto.response;

import com.shortkki.api.calendar.entity.RecipeQueue;

import java.time.LocalDateTime;

public record RecipeQueueDetailResponse(
        Long id,
        Long recipeId,
        String recipeTitle,
        LocalDateTime createdAt
        // TODO: 썸네일 URL 추가
) {
    public static RecipeQueueDetailResponse from(RecipeQueue queue) {
        return new RecipeQueueDetailResponse(
                queue.getId(),
                queue.getRecipe().getId(),
                queue.getRecipe().getBasicInfo().getTitle(),
                queue.getCreatedAt()
        );
    }
}
