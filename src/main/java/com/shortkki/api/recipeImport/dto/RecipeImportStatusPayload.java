package com.shortkki.api.recipeImport.dto;

import com.shortkki.api.source.domain.ImportStatus;
import com.shortkki.api.source.domain.SourceImportHistory;
import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeImportStatusPayload(
        Long importHistoryId,
        ImportStatus status,
        Long recipeId,
        String sourceUrl,
        SourcePlatform platform,
        RecipeImportPreview preview
) {

    public static RecipeImportStatusPayload from(SourceImportHistory history, RecipeImportPreview preview) {
        return new RecipeImportStatusPayload(
                history.getId(),
                history.getStatus(),
                history.getRecipeId(),
                history.getRequestedSourceUrl(),
                history.getPlatform(),
                preview
        );
    }
}
