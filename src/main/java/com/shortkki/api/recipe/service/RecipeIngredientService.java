package com.shortkki.api.recipe.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import com.shortkki.api.recipe.dto.request.IngredientRequest;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientQueryService ingredientQueryService;

    public List<RecipeIngredient> createIngredients(
            Recipe recipe, List<IngredientRequest> ingredientRequests
    ) {
        List<RecipeIngredient> recipeIngredients = ingredientRequests.stream()
                .map(request -> createRecipeIngredient(recipe, request))
                .toList();
        return recipeIngredientRepository.saveAll(recipeIngredients);
    }

    public void deleteByRecipeId(Long recipeId) {
        recipeIngredientRepository.deleteByRecipeId(recipeId);
    }


    private RecipeIngredient createRecipeIngredient(
            Recipe recipe, IngredientRequest request
    ) {
        Ingredient ingredient = ingredientQueryService.findStandardByNameOrNull(request.name());

        String name = ingredient != null ? ingredient.getName() : request.name();

        return RecipeIngredient.create(
                ingredient,
                recipe,
                name,
                request.amount(),
                request.unit()
        );
    }
}
