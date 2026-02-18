package com.shortkki.api.recipeImport.dto;

import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceContentCreator;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeImportPreviewResponse(
        Long sourceContentId,
        Long recipeId,
        SourcePlatform platform,
        SourceContentType contentType,
        String canonicalUrl,
        String title,
        String thumbnailUrl,
        String creatorName,
        String creatorThumbnailUrl
) {

    public static RecipeImportPreviewResponse from(SourceContent content, Long recipeId) {
        SourceContentCreator creator = content.getSourceCreator();
        return new RecipeImportPreviewResponse(
                content.getId(),
                recipeId,
                content.getPlatform(),
                content.getContentType(),
                content.getCanonicalUrl(),
                content.getTitle(),
                content.getThumbnailUrl(),
                creator.getDisplayName(),
                creator.getProfileImgUrl()
        );
    }
}
