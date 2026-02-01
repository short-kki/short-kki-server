package com.shortkki.api.calendar.repository;

import com.shortkki.api.calendar.entity.RecipeQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeQueueRepository extends JpaRepository<RecipeQueue, Long>, RecipeQueueRepositoryCustom {

}
