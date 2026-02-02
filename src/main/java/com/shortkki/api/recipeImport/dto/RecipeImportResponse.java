package com.shortkki.api.recipeImport.dto;

public record RecipeImportResponse(
        Long importHistoryId,
        Long recipeId,
        String title,
        String sourceUrl,
        RecipeImportPreview preview,
        String message
) {

    public static RecipeImportResponse accepted(
            Long importHistoryId,
            String sourceUrl,
            RecipeImportPreview preview) {
        return new RecipeImportResponse(
                importHistoryId,
                null,
                null,
                sourceUrl,
                preview,
                "외부 레시피 파싱을 요청했습니다.");
    }

    public static RecipeImportResponse success(Long recipeId, String title, String sourceUrl) {
        return new RecipeImportResponse(
                null,
                recipeId,
                title,
                sourceUrl,
                null,
                "외부 레시피가 성공적으로 가져와졌습니다.");
    }
}
