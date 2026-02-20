package com.shortkki.api.source.support;

import com.shortkki.api.source.domain.SourcePlatform;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class YouTubeExtractor implements ExternalKeyExtractor {

    private static final Pattern YOUTUBE_URL_PATTERN = Pattern.compile(
            "(?:https?://)?(?:www\\.|m\\.)?(?:"
                    + "youtube\\.com/shorts/([a-zA-Z0-9_-]{11})"
                    + "|youtube\\.com/watch\\?.*v=([a-zA-Z0-9_-]{11})"
                    + "|youtu\\.be/([a-zA-Z0-9_-]{11})"
                    + ")"
    );

    @Override
    public SourcePlatform getPlatform() {
        return SourcePlatform.YOUTUBE;
    }

    @Override
    public boolean supports(String url) {
        if (url == null) {
            throw new IllegalArgumentException("url이 비어있습니다.");
        }
        return YOUTUBE_URL_PATTERN.matcher(url).find();
    }

    @Override
    public String extract(String url) {
        Matcher matcher = YOUTUBE_URL_PATTERN.matcher(url);
        if (!matcher.find()) {
            throw new IllegalArgumentException("지원하지 않는 YouTube URL입니다: " + url);
        }

        for (int i = 1; i <= matcher.groupCount(); i++) {
            String group = matcher.group(i);
            if (group != null) {
                return group;
            }
        }

        throw new IllegalArgumentException("video ID를 추출할 수 없습니다: " + url);
    }
}
