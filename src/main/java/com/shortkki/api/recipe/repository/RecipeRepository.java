package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.Recipe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {

    @Modifying
    @Query("UPDATE Recipe r SET r.bookmarkCount = r.bookmarkCount + 1 WHERE r.id = :recipeId")
    void incrementBookmarkCount(@Param("recipeId") Long recipeId);

    @Modifying
    @Query("UPDATE Recipe r SET r.bookmarkCount = r.bookmarkCount - 1 WHERE r.id = :recipeId AND r.bookmarkCount > 0")
    void decrementBookmarkCount(@Param("recipeId") Long recipeId);

    @Modifying
    @Query("UPDATE Recipe r SET r.bookmarkCount = r.bookmarkCount - 1 WHERE r.id IN :recipeIds AND r.bookmarkCount > 0")
    void decrementBookmarkCountBulk(@Param("recipeIds") List<Long> recipeIds);
}
