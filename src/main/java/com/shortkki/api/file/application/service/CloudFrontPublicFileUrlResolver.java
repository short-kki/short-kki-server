package com.shortkki.api.file.application.service;

import com.shortkki.api.file.entity.FileMetadata;
import java.util.List;
import java.util.Objects;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudFrontPublicFileUrlResolver implements FileUrlResolver {

    private final String publicBaseUrl;

    public CloudFrontPublicFileUrlResolver(
            @Value("${file.cdn.public-base-url}") String publicBaseUrl
    ) {
        String base = Objects.requireNonNull(publicBaseUrl, "publicBaseUrl은 null일 수 없습니다.");
        this.publicBaseUrl = normalizeBaseUrl(base);
    }

    @Override
    public String getUrl(FileMetadata file) {
        String objectKey = file.getObjectKey();

        if (!StringUtils.hasText(objectKey)) {
            return null;
        }
        String key = normalizeObjectKey(objectKey);
        return publicBaseUrl + "/" + key;
    }

    @Override
    public List<String> getUrls(List<FileMetadata> files) {
        return files.stream()
                .map(this::getUrl)
                .toList();
    }

    private static String normalizeBaseUrl(String baseUrl) {
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    private static String normalizeObjectKey(String objectKey) {
        return objectKey.startsWith("/") ? objectKey.substring(1) : objectKey;
    }
}
