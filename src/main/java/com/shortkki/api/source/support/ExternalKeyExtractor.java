package com.shortkki.api.source.support;

import com.shortkki.api.source.domain.SourcePlatform;

public interface ExternalKeyExtractor {

    SourcePlatform getPlatform();

    boolean supports(String url);

    String extract(String url);
}