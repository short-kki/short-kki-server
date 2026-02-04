package com.shortkki.api.publicdata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "public-data.ingredient")
public record PublicDataApiProperties(
        boolean enabled,
        String apiKey,
        String type,
        String apiUrl,
        String urlTemplate,
        int startIndex,
        int endIndex
) {
}
