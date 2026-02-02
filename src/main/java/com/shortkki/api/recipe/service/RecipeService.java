package com.shortkki.api.recipe.service;

import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.file.service.FileMetadataService;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.recipe.dto.request.BasicInfoRequest;
import com.shortkki.api.recipe.dto.request.CategoryInfoRequest;
import com.shortkki.api.recipe.dto.request.RecipeCreateRequest;
import com.shortkki.api.recipe.dto.request.RecipeUpdateRequest;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.service.RecipeBookQueryService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.Objects;
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
    private final MemberRepository memberRepository;
    private final RecipeBookService recipeBookService;
    private final RecipeBookQueryService recipeBookQueryService;
    private final FileMetadataService fileMetadataService;

    public void create(Long memberId, RecipeCreateRequest request) {
        validateRecipeRequest(request);

        Member member = findMemberById(memberId);

        BasicInfoRequest basicInfo = request.basicInfo();
        CategoryInfoRequest categoryInfo = request.categoryInfo();

        Recipe saved = createRecipe(member, basicInfo, categoryInfo);

        recipeStepService.createSteps(saved, request.steps());
        recipeIngredientService.createIngredients(saved, request.ingredients());

        addToDefaultRecipeBook(memberId, saved.getId());
    }

    public void update(Long memberId, Long id, RecipeUpdateRequest request) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        validateRecipeOwnership(recipe, memberId);
        validateUserCreated(recipe);

        Member member = findMemberById(memberId);

        updateRecipeImage(recipe, member, request.basicInfo().imageFileId());

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
            Member member, BasicInfoRequest basicInfo, CategoryInfoRequest categoryInfo) {
        Recipe created = Recipe.createManual(
                member,
                basicInfo.title(),
                basicInfo.description(),
                basicInfo.servingSize(),
                basicInfo.cookingTime(),
                categoryInfo.cuisineType(),
                categoryInfo.mealType(),
                categoryInfo.difficulty());

        Recipe saved = recipeRepository.save(created);

        if (basicInfo.imageFileId() != null) {
            FileMetadata file = fileMetadataService.getById(basicInfo.imageFileId());
            validateFileOwnership(file, member);
            file.bindTarget(FileTargetType.RECIPE_IMG, saved.getId());
            saved.setImageFileId(basicInfo.imageFileId());
        }

        // TODO: 태그 저장 로직 추가

        return saved;
    }

    private void addToDefaultRecipeBook(Long memberId, Long recipeId) {
        RecipeBook defaultBook = recipeBookQueryService.findAllByMemberId(memberId).stream()
                .filter(RecipeBook::getIsDefault)
                .findFirst()
                .orElse(null);

        if (defaultBook == null) {
            recipeBookService.createDefaultForMember(memberId);
            defaultBook = recipeBookQueryService.findAllByMemberId(memberId).stream()
                    .filter(RecipeBook::getIsDefault)
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_BOOK_NOT_FOUND));
        }

        recipeBookService.addRecipeInternal(memberId, defaultBook.getId(), recipeId);
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
    }

    private void validateUserCreated(Recipe recipe) {
        if (recipe.getSourceType() != null && recipe.getSourceType() != RecipeSource.USER) {
            throw new BusinessException(ErrorCode.IMPORTED_RECIPE_NOT_MODIFIABLE);
        }
    }

    private void validateFileOwnership(FileMetadata file, Member member) {
        if (!Objects.equals(file.getUploaderId(), member.getId())) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void validateRecipeOwnership(Recipe recipe, Long memberId) {
        if (!Objects.equals(recipe.getMember().getId(), memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void updateRecipeImage(Recipe recipe, Member member, Long newImageFileId) {
        if (Objects.equals(recipe.getImageFileId(), newImageFileId)) {
            return;
        }

        if (newImageFileId != null) {
            FileMetadata file = fileMetadataService.getById(newImageFileId);
            validateFileOwnership(file, member);
            file.bindTarget(FileTargetType.RECIPE_IMG, recipe.getId());
        }

        recipe.setImageFileId(newImageFileId);
    }
}