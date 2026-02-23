package com.shortkki.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "deep-link")
public record DeepLinkProperties(
        String webDomain,
        Ios ios,
        Android android,
        Store store
) {

    public record Ios(
            String teamId,
            String bundleId
    ) {
    }

    public record Android(
            String packageName,
            List<String> sha256CertFingerprints
    ) {
    }

    public record Store(
            String appStoreUrl,
            String playStoreUrl
    ) {
    }
}
