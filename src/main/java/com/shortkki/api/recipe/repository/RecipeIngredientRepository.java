package com.shortkki.api.recipe.repository;


import com.shortkki.api.recipe.entity.RecipeIngredient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> findByRecipeId(Long recipeId);

    void deleteByRecipeId(Long recipeId);
}
