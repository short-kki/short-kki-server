package com.shortkki.api.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.upload")
public record FileUploadProperties(
        long expireSeconds,
        long maxBytes,
        String publicPrefix,
        String privatePrefix
) {
}