package com.example.short_kki.domain.recipe.repository;

import com.example.short_kki.domain.recipe.entity.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {

}
