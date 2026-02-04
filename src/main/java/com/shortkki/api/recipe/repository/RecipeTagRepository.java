package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.RecipeTag;
import com.shortkki.api.recipe.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long> {

    @Query("""
                select t
                from Tag t
                join RecipeTag rt
                    on rt.tagId = t.id
                where rt.recipeId = :recipeId
            """)
    List<Tag> findTagsByRecipeId(@Param("recipeId") Long recipeId);

    @Query("""
                select t.name
                from RecipeTag rt
                join Tag t
                    on t.id = rt.tagId
                where rt.recipeId = :recipeId
            """)
    List<String> findTagNamesByRecipeId(@Param("recipeId") Long recipeId);

    @Modifying
    @Query("delete from RecipeTag rt where rt.recipeId = :recipeId")
    void deleteByRecipeId(@Param("recipeId") Long recipeId);
}
