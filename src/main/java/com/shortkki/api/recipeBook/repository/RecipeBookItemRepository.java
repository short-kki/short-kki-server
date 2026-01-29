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

    void deleteByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

    void deleteAllByRecipeBookId(Long recipeBookId);
}
