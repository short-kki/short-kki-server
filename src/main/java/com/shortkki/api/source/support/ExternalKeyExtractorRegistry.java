package com.shortkki.api.source.support;

import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ExternalKeyExtractorRegistry {

    private final Map<SourcePlatform, ExternalKeyExtractor> extractorMap;
    private final List<ExternalKeyExtractor> extractors;

    public ExternalKeyExtractorRegistry(List<ExternalKeyExtractor> extractors) {
        this.extractors = extractors;
        this.extractorMap = new EnumMap<>(SourcePlatform.class);
        extractors.forEach(e -> extractorMap.put(e.getPlatform(), e));
    }

    public ExternalKeyExtractor getExtractor(SourcePlatform platform) {
        ExternalKeyExtractor extractor = extractorMap.get(platform);
        if (extractor == null) {
            throw new BadRequestException(ErrorCode.UNSUPPORTED_SOURCE_PLATFORM);
        }
        return extractor;
    }

    public String extractKey(SourcePlatform platform, String url) {
        return getExtractor(platform).extract(url);
    }

    public Optional<SourcePlatform> detectPlatform(String url) {
        return extractors.stream()
                .filter(e -> e.supports(url))
                .map(ExternalKeyExtractor::getPlatform)
                .findFirst();
    }
}