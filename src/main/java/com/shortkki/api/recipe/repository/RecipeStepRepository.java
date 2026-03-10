package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.RecipeStep;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {

    List<RecipeStep> findByRecipeId(Long recipeId);

    List<RecipeStep> findByRecipeIdIn(List<Long> recipeIds);

    void deleteByRecipeId(Long recipeId);
}
