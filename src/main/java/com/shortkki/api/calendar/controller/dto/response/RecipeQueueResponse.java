package com.shortkki.api.calendar.controller.dto.response;

import com.shortkki.api.calendar.entity.RecipeQueue;

import java.time.LocalDateTime;

public record RecipeQueueResponse(
        Long id,
        Long recipeId,
        String recipeTitle,
        LocalDateTime createdAt
) {
    public static RecipeQueueResponse from(RecipeQueue queue) {
        return new RecipeQueueResponse(
                queue.getId(),
                queue.getRecipe().getId(),
                queue.getRecipe().getTitle(),
                queue.getCreatedAt()
        );
    }
}
