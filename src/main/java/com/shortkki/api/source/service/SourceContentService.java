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
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.exception.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
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

    private SourceContent getOrCreateContent(SourcePlatform platform, String externalKey) {
        return sourceContentRepository
                .findByPlatformAndExternalKey(platform, externalKey)
                .orElseGet(() -> {
                    SourceContent content = createSourceContent(platform, externalKey);
                    try {
                        return sourceContentRepository.save(content);
                    } catch (DataIntegrityViolationException e) {
                        // 동시에 다른 요청으로 인해 저장이 실패한 경우, 다시 조회해서 반환
                        return sourceContentRepository.findByPlatformAndExternalKey(platform, externalKey)
                                .orElseThrow(() -> new BusinessException(ErrorCode.SOURCE_CONTENT_ALREADY_EXISTS));
                    }
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
                creator);
    }

    private SourceContentCreator getOrCreateCreator(SourcePlatform platform, SourceCreatorInfo creatorInfo) {
        String externalKey = resolveCreatorKey(platform, creatorInfo);
        String displayName = resolveCreatorName(creatorInfo);
        String thumbnailUrl = (creatorInfo != null) ? creatorInfo.thumbnailUrl() : null;

        return sourceContentCreatorRepository
                .findByPlatformAndExternalKey(platform, externalKey)
                .orElseGet(() -> sourceContentCreatorRepository.save(
                        SourceContentCreator.create(
                                externalKey,
                                platform,
                                displayName,
                                thumbnailUrl)));
    }

    private String resolveCreatorKey(SourcePlatform platform, SourceCreatorInfo info) {
        if (info != null && info.externalKey() != null && !info.externalKey().isBlank()) {
            return info.externalKey();
        }
        return platform.name() + "_UNKNOWN_CREATOR";
    }

    private String resolveCreatorName(SourceCreatorInfo info) {
        if (info != null && info.displayName() != null && !info.displayName().isBlank()) {
            return info.displayName();
        }
        return "Unknown Creator";
    }
}
