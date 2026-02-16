package com.shortkki.api.recipeBook.dto;

import java.util.List;

public record RecipeBookIdsResponse(
        List<Long> recipeBookIds
) {
    public static RecipeBookIdsResponse from(List<Long> recipeBookIds) {
        return new RecipeBookIdsResponse(recipeBookIds);
    }
}
