package com.shortkki.api.source.support;

import com.shortkki.api.source.domain.SourcePlatform;
import org.springframework.stereotype.Component;

@Component
public class YouTubeShortsExtractor implements ExternalKeyExtractor {

    private static final String SHORTS_PATH = "youtube.com/shorts/";

    @Override
    public SourcePlatform getPlatform() {
        return SourcePlatform.YOUTUBE;
    }

    @Override
    public boolean supports(String url) {
        return url.contains(SHORTS_PATH);
    }

    @Override
    public String extract(String url) {
        if (!url.contains(SHORTS_PATH)) {
            throw new IllegalArgumentException("YouTube Shorts URL만 지원합니다: " + url);
        }

        int start = url.indexOf(SHORTS_PATH) + SHORTS_PATH.length();
        int end = url.indexOf('?', start);
        if (end < 0) end = url.length();

        String key = url.substring(start, end);
        int slash = key.indexOf('/');
        if (slash >= 0) key = key.substring(0, slash);

        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("video ID가 비어있습니다");
        }

        return key;
    }
}