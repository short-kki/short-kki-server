package com.shortkki.api.recipe.repository;


import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> findByRecipeId(Long recipeId);

    void deleteByRecipeId(Long recipeId);

    @Query("SELECT ri FROM RecipeIngredient ri JOIN FETCH ri.ingredient WHERE ri.id IN :ids")
    List<RecipeIngredient> findAllByIdsWithIngredient(@Param("ids") List<Long> ids);

    @Query("SELECT ri.ingredient FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId")
    List<Ingredient> findIngredientsByRecipeId(@Param("recipeId") Long recipeId);
}
