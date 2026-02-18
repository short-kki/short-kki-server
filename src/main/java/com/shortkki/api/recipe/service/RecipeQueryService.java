package com.shortkki.api.recipe.service;

import com.shortkki.api.recipe.dto.response.RecipeResponse;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipe.repository.RecipeStepRepository;
import com.shortkki.api.recipe.repository.RecipeTagRepository;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
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
    private final RecipeTagRepository recipeTagRepository;
    private final RecipeBookService recipeBookService;

    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_NOT_FOUND));
    }

    public RecipeResponse getDetail(Long memberId, Long id) {
        Recipe recipe = findById(id);
        return toRecipeResponse(recipe, memberId);
    }

    // TODO: 페이지네이션
    public List<RecipeResponse> getAll() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
                .map(recipe -> toRecipeResponse(recipe, null))
                .toList();
    }

    private RecipeResponse toRecipeResponse(Recipe recipe, Long memberId) {
        List<RecipeStep> steps = recipeStepRepository.findByRecipeId(recipe.getId());
        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(
                recipe.getId());
        List<String> tagNames = findTagNamesByRecipeId(recipe.getId());
        List<Long> ownedRecipeBookIds = memberId == null
                ? List.of()
                : recipeBookService.findOwnedRecipeBookIdsByRecipe(memberId, recipe.getId());
        return RecipeResponse.toDto(recipe, steps, ingredients, tagNames, ownedRecipeBookIds);
    }

    private List<String> findTagNamesByRecipeId(Long recipeId) {
        return recipeTagRepository.findTagNamesByRecipeId(recipeId);
    }
}
