package com.shortkki.api.recipeImport.dto;

import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceContentCreator;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;

public record RecipeImportPreview(
        Long sourceContentId,
        SourcePlatform platform,
        SourceContentType contentType,
        String canonicalUrl,
        String title,
        String thumbnailUrl,
        String creatorName,
        String creatorThumbnailUrl
) {

    public static RecipeImportPreview from(SourceContent content) {
        SourceContentCreator creator = content.getSourceCreator();
        return new RecipeImportPreview(
                content.getId(),
                content.getPlatform(),
                content.getContentType(),
                content.getCanonicalUrl(),
                content.getTitle(),
                content.getThumbnailUrl(),
                creator.getDisplayName(),
                creator.getThumbnailUrl()
        );
    }
}
