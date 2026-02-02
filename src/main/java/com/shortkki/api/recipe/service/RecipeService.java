package com.shortkki.api.recipe.service;

import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.recipe.dto.request.BasicInfoRequest;
import com.shortkki.api.recipe.dto.request.CategoryInfoRequest;
import com.shortkki.api.recipe.dto.request.RecipeCreateRequest;
import com.shortkki.api.recipe.dto.response.RecipeResponse;
import com.shortkki.api.recipe.dto.request.RecipeUpdateRequest;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.service.SourceContentService;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeStepService recipeStepService;
    private final RecipeIngredientService recipeIngredientService;
    private final SourceContentService sourceContentService;

    public RecipeResponse create(RecipeCreateRequest request) {
        validateRecipeRequest(request);

        BasicInfoRequest basicInfo = request.basicInfo();
        CategoryInfoRequest categoryInfo = request.categoryInfo();
        RecipeSource recipeSource = request.recipeSource();

        SourceContent sourceContent = null;
        if (recipeSource == RecipeSource.IMPORT) {
            sourceContent = sourceContentService.resolveSourceContent(request.sourceUrl());
        }

        // TODO: 로그인 연동 후 작성자 세팅
        Recipe saved = createRecipe(basicInfo, categoryInfo, recipeSource, sourceContent);

        List<RecipeStep> savedSteps = recipeStepService.createSteps(saved, request.steps());
        List<RecipeIngredient> savedIngredients = recipeIngredientService.createIngredients(saved, request.ingredients());

        return RecipeResponse.toDto(saved, savedSteps, savedIngredients);
    }

    public void update(Long id, RecipeUpdateRequest request) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        validateUserCreated(recipe);

        recipe.update(request.basicInfo(), request.categoryInfo());

        recipeStepService.deleteByRecipeId(id);
        recipeIngredientService.deleteByRecipeId(id);

        recipeStepService.createSteps(recipe, request.steps());
        recipeIngredientService.createIngredients(recipe, request.ingredients());
    }

    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        validateUserCreated(recipe);

        recipeStepService.deleteByRecipeId(id);
        recipeIngredientService.deleteByRecipeId(id);
        recipeRepository.delete(recipe);
    }

    private Recipe createRecipe(
            BasicInfoRequest basicInfo, CategoryInfoRequest categoryInfo,
            RecipeSource recipeSource, SourceContent sourceContent
    ) {
        Recipe created = switch (recipeSource) {
            case USER -> Recipe.createManual(
                    basicInfo.title(),
                    basicInfo.description(),
                    basicInfo.servingSize(),
                    basicInfo.cookingTime(),
                    categoryInfo.cuisineType(),
                    categoryInfo.mealType(),
                    categoryInfo.difficulty()
            );
            case IMPORT -> Recipe.createImported(
                    basicInfo.title(),
                    basicInfo.description(),
                    basicInfo.servingSize(),
                    basicInfo.cookingTime(),
                    categoryInfo.cuisineType(),
                    categoryInfo.mealType(),
                    categoryInfo.difficulty(),
                    sourceContent
            );
        };

        return recipeRepository.save(created);
    }

    private void validateRecipeRequest(RecipeCreateRequest request) {
        if (request.ingredients() == null || request.ingredients().isEmpty()) {
            throw new BusinessException(ErrorCode.INGREDIENT_REQUIRED);
        }
        if (request.steps() == null || request.steps().isEmpty()) {
            throw new BusinessException(ErrorCode.STEP_REQUIRED);
        }
        if (request.recipeSource() == RecipeSource.IMPORT && isBlankUrl(request.sourceUrl())) {
            throw new BadRequestException(ErrorCode.SOURCE_URL_REQUIRED);
        }
    }

    private boolean isBlankUrl(String url) {
        return url == null || url.isBlank();
    }

    private void validateUserCreated(Recipe recipe) {
        if (recipe.getSourceType() != RecipeSource.USER) {
            throw new BusinessException(ErrorCode.IMPORTED_RECIPE_NOT_MODIFIABLE);
        }
    }
}
