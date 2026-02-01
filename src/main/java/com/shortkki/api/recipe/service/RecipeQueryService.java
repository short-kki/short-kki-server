package com.shortkki.api.recipe.service;

import com.shortkki.api.recipe.dto.RecipeResponse;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipe.repository.RecipeStepRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeQueryService {

    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public Recipe findRecipe(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));
    }

    public RecipeResponse getRecipeDetail(Long id) {
        Recipe recipe = findRecipe(id);

        List<RecipeStep> steps = recipeStepRepository.findByRecipeId(recipe.getId());
        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(
                recipe.getId());

        return RecipeResponse.toDto(recipe, steps, ingredients);
    }

    // TODO: N+1 문제 해결 (Fetch Join 고려)
    // TODO: 페이지네이션 추가
    public List<RecipeResponse> getAll() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
                .map(recipe -> {
                    List<RecipeStep> steps = recipeStepRepository.findByRecipeId(recipe.getId());
                    List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(
                            recipe.getId());
                    return RecipeResponse.toDto(recipe, steps, ingredients);
                })
                .toList();
    }
}
