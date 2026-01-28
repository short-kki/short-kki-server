package com.shortkki.api.source.repository;

import com.shortkki.api.source.domain.SourceContentCreator;
import com.shortkki.api.source.domain.SourcePlatform;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceContentCreatorRepository extends JpaRepository<SourceContentCreator, Long> {

    Optional<SourceContentCreator> findByPlatformAndExternalKey(SourcePlatform platform, String externalKey);
}
