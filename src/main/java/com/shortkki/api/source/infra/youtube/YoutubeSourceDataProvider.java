package com.shortkki.api.source.infra.youtube;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortkki.api.source.service.dto.SourceContentInfo;
import com.shortkki.api.source.service.dto.SourceCreatorInfo;
import com.shortkki.api.source.service.port.SourceDataProvider;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class YoutubeSourceDataProvider implements SourceDataProvider {

    private static final Pattern VIDEO_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{11}$");
    private final String apiKey;
    private final String apiBaseUrl;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public YoutubeSourceDataProvider(
            @Value("${google.credential.api-key}") String apiKey,
            @Value("${google.services.youtube.api.base-url}") String apiBaseUrl,
            RestClient restClient,
            ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.apiBaseUrl = apiBaseUrl;
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public SourceContentInfo getSourceInfo(String videoId) {
        if (!isValidVideoId(videoId)) {
            log.warn("유효하지 않은 YouTube 영상 ID: {}", videoId);
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }

        try {
            String response = restClient.get()
                    .uri(apiBaseUrl + "/videos?part=snippet,status&id={id}&key={key}", videoId, apiKey)
                    .retrieve()
                    .body(String.class);

            if (response == null || response.isBlank()) {
                log.warn("YouTube API 응답이 null입니다: {}", videoId);
                return createDefaultContentInfo(videoId);
            }
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("items");

            if (!items.isArray() || items.isEmpty()) {
                log.warn("YouTube 비디오를 찾을 수 없습니다: {}", videoId);
                return createDefaultContentInfo(videoId);
            }

            JsonNode item = items.get(0);
            JsonNode snippet = item.path("snippet");
            String title = snippet.path("title").asText("제목 없음");
            String channelId = snippet.path("channelId").asText("");
            String thumbnailUrl = snippet.path("thumbnails").path("high").path("url").asText("");
            boolean embeddable = item.path("status").path("embeddable").asBoolean(true);

            return new SourceContentInfo(
                    videoId,
                    title,
                    "https://www.youtube.com/shorts/" + videoId,
                    thumbnailUrl,
                    channelId,
                    embeddable
            );

        } catch (Exception e) {
            log.error("YouTube API 호출 실패: {}", e.getMessage(), e);
            return createDefaultContentInfo(videoId);
        }
    }

    @Override
    public SourceCreatorInfo getSourceCreatorInfo(String channelId) {
        if (channelId == null || channelId.isBlank()) {
            return new SourceCreatorInfo("unknown", "Unknown Creator", null);
        }

        try {
            String response = restClient.get()
                    .uri(apiBaseUrl + "/channels?part=snippet&id={id}&key={key}", channelId,
                            apiKey)
                    .retrieve()
                    .body(String.class);

            if (response == null || response.isBlank()) {
                log.warn("YouTube API 응답이 null입니다: {}", channelId);
                return new SourceCreatorInfo(channelId, "Unknown Creator", null);
            }
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("items");

            if (!items.isArray() || items.isEmpty()) {
                log.warn("YouTube 채널을 찾을 수 없습니다: {}", channelId);
                return new SourceCreatorInfo(channelId, "Unknown Creator", null);
            }

            JsonNode snippet = items.get(0).path("snippet");
            String displayName = snippet.path("title").asText("Unknown Creator");
            String thumbnailUrl = snippet.path("thumbnails").path("default").path("url").asText("");

            return new SourceCreatorInfo(channelId, displayName, thumbnailUrl);

        } catch (Exception e) {
            log.error("YouTube Channel API 호출 실패: {}", e.getMessage(), e);
            return new SourceCreatorInfo(channelId, "Unknown Creator", null);
        }
    }

    private boolean isValidVideoId(String videoId) {
        return videoId != null && VIDEO_ID_PATTERN.matcher(videoId).matches();
    }

    private SourceContentInfo createDefaultContentInfo(String videoId) {
        return new SourceContentInfo(
                videoId,
                "YouTube Video",
                "https://www.youtube.com/shorts/" + videoId,
                null,
                "",
                true
        );
    }
}
