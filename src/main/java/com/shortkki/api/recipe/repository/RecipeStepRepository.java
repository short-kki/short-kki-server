package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.RecipeStep;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {

    List<RecipeStep> findByRecipeId(Long recipeId);

    void deleteByRecipeId(Long recipeId);
}
