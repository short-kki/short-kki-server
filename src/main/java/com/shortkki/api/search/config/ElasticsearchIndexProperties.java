package com.shortkki.api.search.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elasticsearch")
public record ElasticsearchIndexProperties(
        Map<String, IndexConfig> index
) {
    public record IndexConfig(
            String name,
            String settingsPath
    ) {
    }
}
