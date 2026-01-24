package com.example.short_kki.domain.recipeBook.repository;

import com.example.short_kki.domain.recipeBook.entity.RecipeBookItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookItemRepository extends JpaRepository<RecipeBookItem, Long> {

    List<RecipeBookItem> findAllByRecipeBookId(Long recipeBookId);

    boolean existsByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

    void deleteByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);
}
