package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.RecipeTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long> {

    List<RecipeTag> findByRecipeId(Long recipeId);

    void deleteByRecipeId(Long recipeId);

    @Query("""
                select t.name
                from RecipeTag rt
                join Tag t on t.id = rt.tagId
                where rt.recipeId = :recipeId
            """)
    List<String> findTagNamesByRecipeId(@Param("recipeId") Long recipeId);
}
