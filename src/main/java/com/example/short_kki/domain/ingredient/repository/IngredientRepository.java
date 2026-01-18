package com.example.short_kki.domain.ingredient.repository;

import com.example.short_kki.domain.ingredient.entity.Ingredient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>,
        IngredientRepositoryCustom {

    Optional<Ingredient> findByName(String name);

}
