package com.shortkki.api.recipe.service;

import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.recipe.entity.TagSource;
import com.shortkki.api.recipe.dto.request.BasicInfoRequest;
import com.shortkki.api.recipe.dto.request.CategoryInfoRequest;
import com.shortkki.api.recipe.dto.request.RecipeCreateRequest;
import com.shortkki.api.recipe.dto.request.RecipeUpdateRequest;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.vo.RecipeBasicInfo;
import com.shortkki.api.recipe.entity.vo.RecipeCategoryInfo;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipe.repository.RecipeTagRepository;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.service.RecipeBookQueryService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.search.event.RecipeIndexDeleteEvent;
import com.shortkki.api.search.event.RecipeIndexUpsertEvent;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.event.DomainEventPublisher;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeStepService recipeStepService;
    private final RecipeIngredientService recipeIngredientService;
    private final RecipeBookService recipeBookService;
    private final RecipeBookQueryService recipeBookQueryService;
    private final RecipeQueryService recipeQueryService;
    private final FileMetadataQueryService fileMetadataQueryService;
    private final TagService tagService;

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final RecipeTagRepository recipeTagRepository;

    private final DomainEventPublisher domainEventPublisher;

    public void create(Long memberId, RecipeCreateRequest request) {
        validateRecipeRequest(request);

        Member member = findMemberById(memberId);

        BasicInfoRequest basicInfoRequest = request.basicInfo();
        CategoryInfoRequest categoryInfoRequest = request.categoryInfo();

        RecipeBasicInfo basicInfo = new RecipeBasicInfo(
                basicInfoRequest.title(),
                basicInfoRequest.description(),
                basicInfoRequest.servingSize(),
                basicInfoRequest.cookingTime());

        RecipeCategoryInfo categoryInfo = new RecipeCategoryInfo(
                categoryInfoRequest.cuisineType(),
                categoryInfoRequest.mealType(),
                categoryInfoRequest.difficulty());

        Recipe saved = createRecipe(member, basicInfo, categoryInfo, basicInfoRequest.mainImgFileId());

        recipeStepService.createSteps(saved, request.steps());
        recipeIngredientService.createIngredients(saved, request.ingredients());
        tagService.saveRecipeTags(saved.getId(), request.tags(), TagSource.USER);

        addToDefaultRecipeBook(memberId, saved.getId());
        domainEventPublisher.publish(new RecipeIndexUpsertEvent(saved.getId()));
    }

    public void update(Long memberId, Long id, RecipeUpdateRequest request) {
        Recipe recipe = recipeQueryService.findById(id);
        Member member = findMemberById(memberId);

        validateRecipeOwnership(recipe, memberId);
        validateUserCreated(recipe);

        updateRecipeImage(recipe, member, request.basicInfo().mainImgFileId());

        BasicInfoRequest basicInfoRequest = request.basicInfo();
        CategoryInfoRequest categoryInfoRequest = request.categoryInfo();

        RecipeBasicInfo basicInfo = new RecipeBasicInfo(
                basicInfoRequest.title(),
                basicInfoRequest.description(),
                basicInfoRequest.servingSize(),
                basicInfoRequest.cookingTime());
        recipe.updateBasicInfo(basicInfo);

        RecipeCategoryInfo categoryInfo = new RecipeCategoryInfo(
                categoryInfoRequest.cuisineType(),
                categoryInfoRequest.mealType(),
                categoryInfoRequest.difficulty());
        recipe.updateCategoryInfo(categoryInfo);

        recipeStepService.deleteByRecipeId(id);
        recipeIngredientService.deleteByRecipeId(id);
        recipeTagRepository.deleteByRecipeId(id);

        recipeStepService.createSteps(recipe, request.steps());
        recipeIngredientService.createIngredients(recipe, request.ingredients());

        tagService.saveRecipeTags(recipe.getId(), request.tags(), TagSource.USER);
        domainEventPublisher.publish(new RecipeIndexUpsertEvent(recipe.getId()));
    }

    public void delete(Long id) {
        recipeRepository.findById(id).ifPresent(recipe -> {
            recipeStepService.deleteByRecipeId(id);
            recipeIngredientService.deleteByRecipeId(id);
            recipeTagRepository.deleteByRecipeId(id);
            recipeRepository.delete(recipe);
            domainEventPublisher.publish(new RecipeIndexDeleteEvent(id));
        });
    }

    private Recipe createRecipe(
            Member member, RecipeBasicInfo basicInfo, RecipeCategoryInfo categoryInfo, Long imageFileId
    ) {
        Recipe created = Recipe.createManual(member, basicInfo, categoryInfo);
        Recipe saved = recipeRepository.save(created);
        updateRecipeImage(saved, member, imageFileId);

        return saved;
    }

    private void addToDefaultRecipeBook(Long memberId, Long recipeId) {
        RecipeBook defaultBook = recipeBookQueryService.findDefaultByMemberId(memberId)
                .orElse(null);

        if (defaultBook == null) {
            recipeBookService.createDefaultForMember(memberId);
            defaultBook = recipeBookQueryService.findDefaultByMemberId(memberId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_BOOK_NOT_FOUND));
        }

        recipeBookService.addRecipeIfNotExists(memberId, defaultBook.getId(), recipeId);
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
        if (newImageFileId == null) {
            recipe.updateMainImgFile(null);
            return;
        }

        FileMetadata exist = recipe.getMainImgFile();
        if (exist != null && exist.getId().equals(newImageFileId)) {
            return;
        }

        FileMetadata file = fileMetadataQueryService.findById(newImageFileId);
        validateFileOwnership(file, member);
        file.bindTarget(FileTargetType.RECIPE_IMG, recipe.getId());
        recipe.updateMainImgFile(file);
    }
}