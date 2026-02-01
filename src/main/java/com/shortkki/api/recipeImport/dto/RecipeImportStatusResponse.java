package com.shortkki.api.recipeImport.dto;

import com.shortkki.api.source.domain.ImportStatus;
import com.shortkki.api.source.domain.SourceImportHistory;
import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeImportStatusResponse(
        Long importHistoryId,
        ImportStatus status,
        String sourceUrl,
        SourcePlatform platform,
        String detail
) {

    public static RecipeImportStatusResponse from(SourceImportHistory history) {
        return new RecipeImportStatusResponse(
                history.getId(),
                history.getStatus(),
                history.getRequestedSourceUrl(),
                history.getPlatform(),
                history.getRawResponse()
        );
    }
}
