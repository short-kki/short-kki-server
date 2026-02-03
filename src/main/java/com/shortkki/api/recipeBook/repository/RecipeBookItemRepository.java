package com.shortkki.api.recipeBook.repository;

import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeBookItemRepository extends JpaRepository<RecipeBookItem, Long> {

    List<RecipeBookItem> findAllByRecipeBookId(Long recipeBookId);

    boolean existsByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

    long deleteByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

    long deleteAllByRecipeBookId(Long recipeBookId);

    @Query("""
            select i from RecipeBookItem i
            join fetch i.recipe 
            where i.recipeBook.id = :recipeBookId
            """)
    List<RecipeBookItem> findAllByRecipeBookIdWithRecipe(@Param("recipeBookId") Long recipeBookId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                update RecipeBookItem rbi
                   set rbi.recipeBook.id = :toBookId
                 where rbi.recipeBook.id = :fromBookId
                   and rbi.recipe.id = :recipeId
            """)
    long moveRecipe(@Param("fromBookId") Long fromBookId,
            @Param("toBookId") Long toBookId,
            @Param("recipeId") Long recipeId);
}