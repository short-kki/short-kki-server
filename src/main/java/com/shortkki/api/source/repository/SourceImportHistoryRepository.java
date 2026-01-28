package com.shortkki.api.source.repository;

import com.shortkki.api.source.domain.SourceImportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceImportHistoryRepository extends JpaRepository<SourceImportHistory, Long> {

}
