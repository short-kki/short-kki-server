package com.shortkki.api.source.repository;

import com.shortkki.api.source.domain.SourceContent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SourceContentRepository extends JpaRepository<SourceContent, Long>, SourceContentRepositoryCustom {

    @Query("""
            select scc
            from SourceContent scc
            join fetch scc.sourceCreator sc
            where scc.id = :id
            """)
    Optional<SourceContent> findById(Long id);
}
