package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.RecipeTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long> {

    List<RecipeTag> findByRecipeId(Long recipeId);

    void deleteByRecipeId(Long recipeId);
}
