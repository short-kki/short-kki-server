package com.shortkki.api.source.repository;

import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourcePlatform;

import java.util.Optional;

public interface SourceContentRepositoryCustom {

    Optional<SourceContent> findByPlatformAndExternalKey(SourcePlatform platform, String externalKey);

    Optional<SourceContent> findByIdWithCreator(Long id);
}
