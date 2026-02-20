package com.shortkki.api.source.service;

import com.shortkki.api.recipeImport.dto.RecipeImportPreviewResponse;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.source.domain.ContentStatus;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.source.repository.SourceContentRepository;
import com.shortkki.api.source.service.dto.SourceContentInfo;
import com.shortkki.api.source.service.dto.SourceCreatorInfo;
import com.shortkki.api.source.service.port.SourceDataProvider;
import com.shortkki.api.source.support.ExternalKeyExtractorRegistry;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SourceContentQueryService {

    private final SourceDataProvider sourceDataProvider;
    private final ExternalKeyExtractorRegistry extractorRegistry;
    private final SourceContentRepository sourceContentRepository;
    private final RecipeRepository recipeRepository;

    @Value("${shortkki.source.max-duration-seconds}")
    private int maxDurationSeconds;

    public SourceContent findById(long id) {
        return sourceContentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SOURCE_CONTENT_NOT_FOUND));
    }

    public RecipeImportPreviewResponse getSourceContentPreview(String sourceUrl) {
        SourcePlatform platform = extractorRegistry.detectPlatform(sourceUrl)
                .orElseThrow(() -> new BadRequestException(ErrorCode.UNSUPPORTED_SOURCE_PLATFORM));
        String externalKey = extractorRegistry.extractKey(platform, sourceUrl);

        SourceContent existingContent = sourceContentRepository.findByPlatformAndExternalKey(
                        platform, externalKey)
                .orElse(null);
        if (existingContent != null) {
            Long recipeId = recipeRepository.findIdBySourceContentId(existingContent.getId())
                    .orElse(null);
            return RecipeImportPreviewResponse.from(existingContent, recipeId);
        }

        SourceContentInfo contentInfo = sourceDataProvider.getSourceInfo(externalKey);
        validateDuration(contentInfo);
        SourceCreatorInfo creatorInfo = sourceDataProvider.getSourceCreatorInfo(contentInfo.channelId());

        return new RecipeImportPreviewResponse(
                null,
                null,
                platform,
                SourceContentType.VIDEO,
                contentInfo.canonicalUrl(),
                contentInfo.title(),
                contentInfo.thumbnailUrl(),
                creatorInfo.displayName(),
                creatorInfo.thumbnailUrl(),
                ContentStatus.AVAILABLE,
                contentInfo.embeddable()
        );
    }

    private void validateDuration(SourceContentInfo contentInfo) {
        Integer duration = contentInfo.durationSeconds();
        if (duration == null) {
            log.warn("영상 길이를 확인할 수 없습니다. externalKey={}", contentInfo.externalKey());
            throw new BadRequestException(ErrorCode.SOURCE_DURATION_UNKNOWN);
        }
        if (duration > maxDurationSeconds) {
            log.warn("영상 길이 초과: {}초 (제한: {}초), externalKey={}", duration, maxDurationSeconds, contentInfo.externalKey());
            throw new BadRequestException(ErrorCode.SOURCE_DURATION_EXCEEDED);
        }
    }
}

