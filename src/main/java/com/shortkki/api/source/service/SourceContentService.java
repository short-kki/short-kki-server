package com.shortkki.api.source.service;

import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceContentCreator;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.source.repository.SourceContentCreatorRepository;
import com.shortkki.api.source.repository.SourceContentRepository;
import com.shortkki.api.source.service.dto.SourceContentInfo;
import com.shortkki.api.source.service.dto.SourceCreatorInfo;
import com.shortkki.api.source.service.port.SourceDataProvider;
import com.shortkki.api.source.support.ExternalKeyExtractorRegistry;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SourceContentService {

    private final SourceDataProvider sourceDataProvider;
    private final ExternalKeyExtractorRegistry extractorRegistry;
    private final SourceContentRepository sourceContentRepository;
    private final SourceContentCreatorRepository sourceContentCreatorRepository;

    public SourceContent resolveSourceContent(String sourceUrl) {
        if (sourceUrl == null || sourceUrl.isBlank()) {
            throw new BadRequestException(ErrorCode.SOURCE_URL_REQUIRED);
        }

        SourcePlatform platform = detectPlatform(sourceUrl);
        String externalKey = extractorRegistry.extractKey(platform, sourceUrl);

        return getOrCreateContent(platform, externalKey);
    }

    private SourcePlatform detectPlatform(String url) {
        return extractorRegistry.detectPlatform(url)
                .orElseThrow(() -> new BadRequestException(ErrorCode.UNSUPPORTED_SOURCE_PLATFORM));
    }

    private SourceContent getOrCreateContent(
            SourcePlatform platform, String externalKey
    ) {
        return sourceContentRepository
                .findByPlatformAndExternalKey(platform, externalKey)
                .orElseGet(() -> {
                    SourceContent content = createSourceContent(platform, externalKey);
                    return sourceContentRepository.save(content);
                });
    }

    private SourceContent createSourceContent(SourcePlatform platform, String externalKey) {
        SourceContentInfo sourceContentInfo = sourceDataProvider.getSourceInfo(externalKey);

        SourceCreatorInfo creatorInfo = sourceDataProvider.getSourceCreatorInfo(sourceContentInfo.channelId());
        SourceContentCreator creator = getOrCreateCreator(platform, creatorInfo);

        return SourceContent.create(
                sourceContentInfo.title(),
                sourceContentInfo.canonicalUrl(),
                sourceContentInfo.externalKey(),
                platform,
                sourceContentInfo.thumbnailUrl(),
                // TODO: 컨텐츠 타입 결정하는 방법 정하기 (url / ai 변환)
                SourceContentType.VIDEO,
                creator
        );
    }

    private SourceContentCreator getOrCreateCreator(SourcePlatform platform, SourceCreatorInfo creatorInfo) {
        return sourceContentCreatorRepository
                .findByPlatformAndExternalKey(platform, creatorInfo.externalKey())
                .orElseGet(() -> sourceContentCreatorRepository.save(
                        SourceContentCreator.create(
                                creatorInfo.externalKey(),
                                platform,
                                creatorInfo.displayName(),
                                creatorInfo.thumbnailUrl()
                        )
                ));
    }
}
