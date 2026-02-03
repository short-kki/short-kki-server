package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.RecipeTag;
import com.shortkki.api.recipe.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long> {

    @Query("SELECT t FROM Tag t JOIN RecipeTag rt ON rt.tagId = t.id WHERE rt.recipeId = :recipeId")
    List<Tag> findTagsByRecipeId(@Param("recipeId") Long recipeId);
}
