package com.shortkki.api.recipeImport.dto;

public record RecipeImportResponse(
        Long recipeId,
        String title,
        String sourceUrl,
        String message
) {

    public static RecipeImportResponse success(Long recipeId, String title, String sourceUrl) {
        return new RecipeImportResponse(recipeId, title, sourceUrl, "외부 레시피가 성공적으로 가져와졌습니다.");
    }
}
