package com.shortkki.api.calendar.repository;

import com.shortkki.api.calendar.entity.RecipeCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCalendarRepository extends JpaRepository<RecipeCalendar, Long>, RecipeCalendarRepositoryCustom {

}
