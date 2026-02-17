package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Modifying
    @Query("UPDATE Recipe r SET r.bookmarkCount = r.bookmarkCount + 1 WHERE r.id = :recipeId")
    void incrementBookmarkCount(@Param("recipeId") Long recipeId);

    @Modifying
    @Query("UPDATE Recipe r SET r.bookmarkCount = r.bookmarkCount - 1 WHERE r.id = :recipeId AND r.bookmarkCount > 0")
    void decrementBookmarkCount(@Param("recipeId") Long recipeId);

    @Modifying
    @Query("UPDATE Recipe r SET r.bookmarkCount = r.bookmarkCount - 1 WHERE r.id IN :recipeIds AND r.bookmarkCount > 0")
    void decrementBookmarkCountBulk(@Param("recipeIds") List<Long> recipeIds);

    boolean existsBySourceContentId(Long sourceContentId);

    @Query("select r.id from Recipe r where r.sourceContent.id = :sourceContentId")
    Optional<Long> findIdBySourceContentId(@Param("sourceContentId") Long sourceContentId);
}
