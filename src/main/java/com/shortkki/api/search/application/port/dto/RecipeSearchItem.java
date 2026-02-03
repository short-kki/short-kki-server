package com.shortkki.api.search.application.port.dto;

public record RecipeSearchItem(
        Long id,
        String title,
        int bookmarkCount,
        String thumbnailUrl,
        String authorName
) {

}
