package com.example.short_kki.domain.recipe.service;

import com.example.short_kki.domain.ingredient.entity.Ingredient;
import com.example.short_kki.domain.ingredient.repository.IngredientRepository;
import com.example.short_kki.domain.recipe.dto.RecipeCreateRequest;
import com.example.short_kki.domain.recipe.dto.RecipeResponse;
import com.example.short_kki.domain.recipe.entity.Recipe;
import com.example.short_kki.domain.recipe.entity.RecipeIngredient;
import com.example.short_kki.domain.recipe.entity.RecipeStep;
import com.example.short_kki.domain.recipe.repository.RecipeIngredientRepository;
import com.example.short_kki.domain.recipe.repository.RecipeRepository;
import com.example.short_kki.domain.recipe.repository.RecipeStepRepository;
import com.example.short_kki.global.exception.BusinessException;
import com.example.short_kki.global.exception.ErrorCode;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * 레시피 생성
     */
    @Transactional
    public RecipeResponse create(RecipeCreateRequest request) {
        // TODO: 로그인 연동 후 작성자 세팅
        Recipe recipe = request.toEntity();
        Recipe saved = recipeRepository.save(recipe);

        List<RecipeStep> savedSteps = new ArrayList<>();
        if (request.steps() != null) {
            request.steps().forEach(stepInfo -> {
                RecipeStep step = RecipeStep.create(saved, stepInfo.stepOrder(),
                        stepInfo.description());

                recipeStepRepository.save(step);
                savedSteps.add(step);
            });
        }

        List<RecipeIngredient> savedIngredients = new ArrayList<>();
        if (request.ingredients() != null) {

            request.ingredients().forEach(ingredientInfo -> {
                Ingredient ingredient = ingredientRepository.findByName(ingredientInfo.name())
                        .orElseGet(() -> {
                            Ingredient newIngredient = Ingredient.create(ingredientInfo.name(),
                                    ingredientInfo.unit());
                            return ingredientRepository.save(newIngredient);
                        });

                RecipeIngredient newRecipeIngredient = RecipeIngredient.create(ingredient, saved,
                        ingredientInfo.amount());
                recipeIngredientRepository.save(newRecipeIngredient);
                savedIngredients.add(newRecipeIngredient);
            });

        }

        return RecipeResponse.toDto(saved, savedSteps, savedIngredients);
    }

    /**
     * 레시피 단건 조회
     */
    public RecipeResponse findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        List<RecipeStep> steps = recipeStepRepository.findByRecipeId(recipe.getId());

        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(
                recipe.getId());

        return RecipeResponse.toDto(recipe, steps, ingredients);
    }

    /**
     * 레시피 전체 조회
     * TODO: N+1 문제 해결 (Fetch Join 고려)
     * TODO: 페이지네이션 추가
     */
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

    /**
     * 레시피 삭제
     *
     */
    @Transactional
    public void delete(Long id) {
        // TODO : 작성자 권한 확인
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        recipeStepRepository.deleteByRecipeId(id);
        recipeIngredientRepository.deleteByRecipeId(id);

        recipeRepository.delete(recipe);
    }
}
