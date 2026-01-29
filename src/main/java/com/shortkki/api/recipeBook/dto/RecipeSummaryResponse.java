package com.shortkki.api.recipeBook.dto;


import com.shortkki.api.recipe.entity.Recipe;

public record RecipeSummaryResponse(
        Long id,
        String title,
        Integer bookmarkCount,
        // TODO: Recipe 엔티티에 thumbnailUrl 필드 추가 후 활성화
        String thumbnailUrl,
        // TODO: Recipe 엔티티에 author(Member) 필드 추가 후 활성화
        String authorName) {

    public static RecipeSummaryResponse from(Recipe recipe) {
        return new RecipeSummaryResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getBookmarkCount(),
                null, // TODO: recipe.getThumbnailUrl()
                null // TODO: recipe.getAuthor().getName()
        );
    }
}
