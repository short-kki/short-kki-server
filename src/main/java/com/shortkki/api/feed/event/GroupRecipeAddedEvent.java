package com.shortkki.api.feed.event;

public record GroupRecipeAddedEvent(
        long groupId,
        long memberId,
        long recipeId
) {

}
