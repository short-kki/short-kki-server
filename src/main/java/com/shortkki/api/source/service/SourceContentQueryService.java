package com.shortkki.api.source.service;

import com.shortkki.api.recipeImport.dto.RecipeImportPreviewResponse;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.repository.SourceContentRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SourceContentQueryService {

    private final SourceContentService sourceContentService;

    private final SourceContentRepository sourceContentRepository;

    public SourceContent findById(long id) {
        return sourceContentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SOURCE_CONTENT_NOT_FOUND));
    }

    public RecipeImportPreviewResponse getSourceContentPreview(String url) {
        SourceContent sourceContent = sourceContentService.resolveSourceContent(url);
        return RecipeImportPreviewResponse.from(sourceContent);
    }
}


