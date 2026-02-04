package com.shortkki.api.curation.repository;

import com.shortkki.api.curation.entity.Curation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurationRepository extends JpaRepository<Curation, Long> {

    boolean existsByTitle(String title);

    @Query(value = """
        SELECT *
        FROM curation c
        WHERE c.is_active = true
          AND (
                c.day_types IS NULL
                OR c.day_types = ''
                OR c.day_types LIKE CONCAT('%', :dayType, '%')
              )
          AND (
                c.time_types IS NULL
                OR c.time_types = ''
                OR c.time_types LIKE CONCAT('%', :timeType, '%')
              )
        ORDER BY c.created_at DESC
        """, nativeQuery = true)
    Slice<Curation> findMatchingCurations(
            @Param("dayType") String dayType,
            @Param("timeType") String timeType,
            Pageable pageable
    );
}
