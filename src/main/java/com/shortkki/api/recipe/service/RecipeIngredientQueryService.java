package com.shortkki.api.recipe.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeIngredientQueryService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    public List<Ingredient> findIngredientsByRecipeId(Long recipeId) {
        return recipeIngredientRepository.findIngredientsByRecipeId(recipeId);
    }
}
