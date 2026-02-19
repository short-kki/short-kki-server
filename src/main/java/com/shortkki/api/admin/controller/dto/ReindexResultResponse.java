package com.shortkki.api.admin.controller.dto;

public record ReindexResultResponse(
        int totalRecipeCount,
        int indexedCount,
        int failedCount
) {
}
