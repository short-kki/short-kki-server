package com.shortkki.api.calendar.repository;

import com.shortkki.api.calendar.entity.RecipeCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;

public interface RecipeCalendarRepository extends JpaRepository<RecipeCalendar, Long>,
        RecipeCalendarRepositoryCustom {

    @Query("""
            select coalesce(max(rc.sortOrder), 0)
            from RecipeCalendar rc
            where rc.member.id = :memberId
              and rc.scheduledDate = :date
            """)
    int findPersonalMaxSortOrder(long memberId, LocalDate date);

    @Query("""
            select coalesce(max(rc.sortOrder), 0)
            from RecipeCalendar rc
            where rc.group.id = :groupId
              and rc.scheduledDate = :date
            """)
    int findGroupMaxSortOrder(long groupId, LocalDate date);
}
