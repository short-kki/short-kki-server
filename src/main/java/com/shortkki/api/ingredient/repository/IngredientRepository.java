package com.shortkki.api.ingredient.repository;

import com.shortkki.api.ingredient.entity.Ingredient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepositoryCustom {

    Optional<Ingredient> findByName(String name);
}
