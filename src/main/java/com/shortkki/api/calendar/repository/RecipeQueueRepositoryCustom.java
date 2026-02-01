package com.shortkki.api.calendar.repository;

import com.shortkki.api.calendar.entity.RecipeQueue;

import java.util.List;

public interface RecipeQueueRepositoryCustom {

    List<RecipeQueue> findAllByMemberIdWithRecipe(Long memberId);
}
