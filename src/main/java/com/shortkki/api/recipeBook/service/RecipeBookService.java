package com.shortkki.api.recipeBook.service;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.recipeBook.dto.RecipeBookCreateRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookDetailResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookListResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookRecipeSortType;
import com.shortkki.api.recipeBook.dto.RecipeBookReorderRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeBookService {

    private final RecipeBookReadService recipeBookReadService;
    private final RecipeBookCommandService recipeBookCommandService;

    public RecipeBookResponse create(Long memberId, RecipeBookCreateRequest request) {
        return recipeBookCommandService.create(memberId, request);
    }

    public RecipeBookListResponse findAllByMember(Long memberId, Pageable pageable) {
        return recipeBookReadService.findAllByMember(memberId, pageable);
    }

    public List<Long> findOwnedRecipeBookIdsByRecipe(Long memberId, Long recipeId) {
        return recipeBookReadService.findOwnedRecipeBookIdsByRecipe(memberId, recipeId);
    }

    public RecipeBookListResponse findAllByGroup(Long memberId, Long groupId, Pageable pageable) {
        return recipeBookReadService.findAllByGroup(memberId, groupId, pageable);
    }

    public RecipeBookDetailResponse findById(
            Long memberId,
            Long id,
            Pageable pageable,
            RecipeBookRecipeSortType sortType
    ) {
        return recipeBookReadService.findById(memberId, id, pageable, sortType);
    }

    public void updateTitle(Long memberId, Long id, RecipeBookUpdateRequest request) {
        recipeBookCommandService.updateTitle(memberId, id, request);
    }

    public void delete(Long memberId, Long id) {
        recipeBookCommandService.delete(memberId, id);
    }

    public void addRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        recipeBookCommandService.addRecipe(memberId, recipeBookId, recipeId);
    }

    public void addRecipeIfNotExists(Long memberId, Long recipeBookId, Long recipeId) {
        recipeBookCommandService.addRecipeIfNotExists(memberId, recipeBookId, recipeId);
    }

    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        recipeBookCommandService.removeRecipe(memberId, recipeBookId, recipeId);
    }

    public void createDefaultForMember(Member member) {
        recipeBookCommandService.createDefaultForMember(member);
    }

    public void createDefaultForMember(Long memberId) {
        recipeBookCommandService.createDefaultForMember(memberId);
    }

    public void createDefaultForGroup(Long groupId, String groupName) {
        recipeBookCommandService.createDefaultForGroup(groupId, groupName);
    }

    public void reorder(Long memberId, RecipeBookReorderRequest request) {
        recipeBookCommandService.reorder(memberId, request);
    }

    public void moveRecipe(Long memberId, Long fromBookId, Long toBookId, Long recipeId) {
        recipeBookCommandService.moveRecipe(memberId, fromBookId, toBookId, recipeId);
    }
}
