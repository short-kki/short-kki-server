package com.example.short_kki.domain.recipe.repository;


import com.example.short_kki.domain.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

}
