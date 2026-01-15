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
        // 1. 레시피 저장
        Recipe recipe = request.toEntity();
        Recipe saved = recipeRepository.save(recipe);

        // 2. 있으면, 조리순서 저장
        if (request.steps() != null) {
            request.steps().forEach(stepInfo -> {
                RecipeStep step = RecipeStep.create(saved, stepInfo.stepOrder(),
                        stepInfo.description());

                recipeStepRepository.save(step);
            });
        }
        // 3. 있으면, 재료 저장
        if (request.ingredients() != null) {
            // 3.1 재료 레포지토리 존재 여부 체크
            request.ingredients().forEach(ingredientInfo -> {
                Ingredient ingredient = ingredientRepository.findByName(ingredientInfo.name())
                        .orElseGet(() -> {
                            Ingredient newIngredient = Ingredient.create(ingredientInfo.name(),
                                    ingredientInfo.unit());
                            return ingredientRepository.save(newIngredient);
                        });

                // 3.2 중간테이블 저장
                RecipeIngredient newRecipeIngredient = RecipeIngredient.create(ingredient, saved,
                        ingredientInfo.amount());
                recipeIngredientRepository.save(newRecipeIngredient);
            });


        }
        // 4. 있으면, 레시피 태그 저장 (TODO: 나중에 구현)
        // 5. 있으면 , 이미지 저장 (TODO: 나중에 구현)
        // 6. 응답 반환
        return RecipeResponse.from(saved);
    }

    /**
     * 레시피 단건 조회
     */
    public RecipeResponse findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다. id=" + id));
        return RecipeResponse.from(recipe);
    }

    /**
     * 레시피 전체 조회
     */
    public List<RecipeResponse> findAll() {
        return recipeRepository.findAll().stream()
                .map(RecipeResponse::from)
                .toList();
    }

    /**
     * 레시피 삭제
     */
    @Transactional
    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다. id=" + id));
        recipeRepository.delete(recipe);
    }
}
