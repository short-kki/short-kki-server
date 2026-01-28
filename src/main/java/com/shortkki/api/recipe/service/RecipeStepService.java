package com.shortkki.api.recipe.service;

import com.shortkki.api.recipe.dto.StepRequest;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.repository.RecipeStepRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeStepService {

    private final RecipeStepRepository recipeStepRepository;

    public List<RecipeStep> createSteps(Recipe recipe, List<StepRequest> stepRequests) {
        List<RecipeStep> steps = new ArrayList<>();
        for (int i = 0; i < stepRequests.size(); i++) {
            steps.add(RecipeStep.create(recipe, i + 1, stepRequests.get(i).description()));
        }
        return recipeStepRepository.saveAll(steps);
    }

    public void deleteByRecipeId(Long recipeId) {
        recipeStepRepository.deleteByRecipeId(recipeId);
    }
}
