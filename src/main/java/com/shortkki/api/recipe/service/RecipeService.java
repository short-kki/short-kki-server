package com.shortkki.api.recipe.service;

import com.shortkki.api.recipe.constant.SourceType;
import com.shortkki.api.recipe.dto.BasicInfoRequest;
import com.shortkki.api.recipe.dto.CategoryInfoRequest;
import com.shortkki.api.recipe.dto.RecipeCreateRequest;
import com.shortkki.api.recipe.dto.RecipeResponse;
import com.shortkki.api.recipe.dto.RecipeUpdateRequest;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.service.RecipeBookQueryService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.service.SourceContentService;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.exception.NotFoundException;
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
    private final MemberRepository memberRepository;
    private final RecipeBookService recipeBookService;
    private final RecipeBookQueryService recipeBookQueryService;

    public RecipeResponse create(Long memberId, RecipeCreateRequest request) {
        validateRecipeRequest(request);

        Member member = findMemberById(memberId);

        BasicInfoRequest basicInfo = request.basicInfo();
        CategoryInfoRequest categoryInfo = request.categoryInfo();
        SourceType sourceType = request.sourceType();

        SourceContent sourceContent = null;
        if (sourceType == SourceType.IMPORTED) {
            sourceContent = sourceContentService.resolveSourceContent(request.sourceUrl());
        }

        Recipe saved = createRecipe(member, basicInfo, categoryInfo, sourceType, sourceContent);

        List<RecipeStep> savedSteps = recipeStepService.createSteps(saved, request.steps());
        List<RecipeIngredient> savedIngredients = recipeIngredientService.createIngredients(saved,
                request.ingredients());

        addToDefaultRecipeBook(memberId, saved.getId());

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
            Member member, BasicInfoRequest basicInfo, CategoryInfoRequest categoryInfo,
            SourceType sourceType, SourceContent sourceContent
    ) {
        Recipe created = switch (sourceType) {
            case USER_CREATED -> Recipe.createManual(
                    member,
                    basicInfo.title(),
                    basicInfo.description(),
                    basicInfo.servingSize(),
                    basicInfo.cookingTime(),
                    categoryInfo.cuisineType(),
                    categoryInfo.mealType(),
                    categoryInfo.difficulty());
            case IMPORTED -> Recipe.createImported(
                    member,
                    basicInfo.title(),
                    basicInfo.description(),
                    basicInfo.servingSize(),
                    basicInfo.cookingTime(),
                    categoryInfo.cuisineType(),
                    categoryInfo.mealType(),
                    categoryInfo.difficulty(),
                    sourceContent);
        };

        return recipeRepository.save(created);
    }

    private void addToDefaultRecipeBook(Long memberId, Long recipeId) {
        recipeBookQueryService.findAllByMemberId(memberId).stream()
                .filter(RecipeBook::getIsDefault)
                .findFirst()
                .ifPresent(defaultBook -> recipeBookService.addRecipeInternal(defaultBook.getId(),
                        recipeId));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateRecipeRequest(RecipeCreateRequest request) {
        if (request.ingredients() == null || request.ingredients().isEmpty()) {
            throw new BusinessException(ErrorCode.INGREDIENT_REQUIRED);
        }
        if (request.steps() == null || request.steps().isEmpty()) {
            throw new BusinessException(ErrorCode.STEP_REQUIRED);
        }
        if (request.sourceType() == SourceType.IMPORTED && isBlankUrl(request.sourceUrl())) {
            throw new BadRequestException(ErrorCode.SOURCE_URL_REQUIRED);
        }
    }

    private boolean isBlankUrl(String url) {
        return url == null || url.isBlank();
    }

    private void validateUserCreated(Recipe recipe) {
        if (recipe.getSourceType() != null && recipe.getSourceType() != SourceType.USER_CREATED) {
            throw new BusinessException(ErrorCode.IMPORTED_RECIPE_NOT_MODIFIABLE);
        }
    }
}
