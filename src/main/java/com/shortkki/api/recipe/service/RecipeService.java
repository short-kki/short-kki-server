package com.shortkki.api.recipe.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import com.shortkki.api.recipe.constant.SourceType;
import com.shortkki.api.recipe.dto.IngredientRequest;
import com.shortkki.api.recipe.dto.RecipeCreateRequest;
import com.shortkki.api.recipe.dto.RecipeResponse;
import com.shortkki.api.recipe.dto.RecipeUpdateRequest;
import com.shortkki.api.recipe.dto.StepRequest;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipe.repository.RecipeStepRepository;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public RecipeResponse create(RecipeCreateRequest request) {
        validateRecipeRequest(request);

        // TODO: 로그인 연동 후 작성자 세팅
        Recipe recipe = request.toEntity();
        Recipe saved = recipeRepository.save(recipe);

        List<RecipeStep> savedSteps = createSteps(saved, request.steps());
        List<RecipeIngredient> savedIngredients = createIngredients(saved, request.ingredients());

        return RecipeResponse.toDto(saved, savedSteps, savedIngredients);
    }

    @Transactional
    public void update(Long id, RecipeUpdateRequest request) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        validateUserCreated(recipe);

        recipe.update(request.basicInfo(), request.categoryInfo());

        recipeStepRepository.deleteByRecipeId(id);
        recipeIngredientRepository.deleteByRecipeId(id);

        createSteps(recipe, request.steps());
        createIngredients(recipe, request.ingredients());
    }

    public RecipeResponse findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        List<RecipeStep> steps = recipeStepRepository.findByRecipeId(recipe.getId());
        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(
                recipe.getId());

        return RecipeResponse.toDto(recipe, steps, ingredients);
    }

    // TODO: N+1 문제 해결 (Fetch Join 고려)
    // TODO: 페이지네이션 추가
    public List<RecipeResponse> findAll() {
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

    @Transactional
    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        validateUserCreated(recipe);

        recipeStepRepository.deleteByRecipeId(id);
        recipeIngredientRepository.deleteByRecipeId(id);

        recipeRepository.delete(recipe);
    }

    private List<RecipeStep> createSteps(Recipe recipe, List<StepRequest> stepRequests) {
        List<RecipeStep> steps = new ArrayList<>();
        for (int i = 0; i < stepRequests.size(); i++) {
            steps.add(RecipeStep.create(recipe, i + 1, stepRequests.get(i).description()));
        }
        return recipeStepRepository.saveAll(steps);
    }

    private List<RecipeIngredient> createIngredients(Recipe recipe,
            List<IngredientRequest> ingredientRequests) {
        List<RecipeIngredient> recipeIngredients = ingredientRequests.stream()
                .map(info -> {
                    Ingredient ingredient = ingredientRepository.findByName(info.name())
                            .orElseGet(() -> ingredientRepository.save(
                                    Ingredient.create(info.name(), info.unit())));
                    return RecipeIngredient.create(ingredient, recipe, info.amount());
                })
                .toList();
        return recipeIngredientRepository.saveAll(recipeIngredients);
    }

    private void validateRecipeRequest(RecipeCreateRequest request) {
        if (request.ingredients() == null || request.ingredients().isEmpty()) {
            throw new BusinessException(ErrorCode.INGREDIENT_REQUIRED);
        }
        if (request.steps() == null || request.steps().isEmpty()) {
            throw new BusinessException(ErrorCode.STEP_REQUIRED);
        }
    }

    private void validateUserCreated(Recipe recipe) {
        if (recipe.getSourceType() != null && recipe.getSourceType() != SourceType.USER_CREATED) {
            throw new BusinessException(ErrorCode.IMPORTED_RECIPE_NOT_MODIFIABLE);
        }
    }
}
