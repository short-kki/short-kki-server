package com.shortkki.api.source.repository;

import com.shortkki.api.source.domain.SourceContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceContentRepository extends JpaRepository<SourceContent, Long>, SourceContentRepositoryCustom {

}
