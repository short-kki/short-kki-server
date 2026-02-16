package com.shortkki.api.recipeBook.service;

import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.recipeBook.dto.RecipeBookDetailResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookListResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookRecipeSortType;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeSummaryResponse;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeBookReadService {

    private final RecipeBookQueryService recipeBookQueryService;
    private final RecipeBookValidationService recipeBookValidationService;
    private final GroupMemberValidationService groupMemberValidationService;
    private final RecipeBookItemRepository recipeBookItemRepository;
    private final RecipeBookListResponseAssembler recipeBookListResponseAssembler;

    public RecipeBookListResponse getMemberRecipeBooks(Long memberId, Pageable pageable) {
        Slice<RecipeBook> recipeBookSlice = recipeBookQueryService.findSliceByMemberId(memberId,
                pageable);
        return recipeBookListResponseAssembler.assemble(recipeBookSlice);
    }

    public List<Long> findOwnedRecipeBookIdsByRecipe(Long memberId, Long recipeId) {
        return recipeBookItemRepository.findOwnedRecipeBookIdsByRecipeIdAndMemberId(recipeId,
                memberId);
    }

    public RecipeBookListResponse getGroupRecipeBooks(Long memberId, Long groupId,
            Pageable pageable) {
        groupMemberValidationService.validateGroupMember(memberId, groupId);
        Slice<RecipeBook> recipeBookSlice = recipeBookQueryService.findSliceByGroupId(groupId,
                pageable);
        return recipeBookListResponseAssembler.assemble(recipeBookSlice);
    }

    public RecipeBookDetailResponse getRecipeBookDetail(
            Long memberId,
            Long id,
            Pageable pageable,
            RecipeBookRecipeSortType sortType
    ) {
        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(id);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);

        Slice<RecipeBookItem> slice = findRecipeItemsBySort(id, pageable, sortType);
        List<RecipeSummaryResponse> recipes = slice.getContent().stream()
                .map(item -> RecipeSummaryResponse.from(item.getRecipe()))
                .toList();
        long recipeCount = recipeBookItemRepository.countByRecipeBookId(id);

        RecipeBookResponse response = RecipeBookResponse.from(recipeBook, recipes, recipeCount);
        return new RecipeBookDetailResponse(response, SlicePageInfoResponse.from(slice));
    }

    private Slice<RecipeBookItem> findRecipeItemsBySort(
            Long recipeBookId,
            Pageable pageable,
            RecipeBookRecipeSortType sortType
    ) {
        if (sortType == null) {
            return recipeBookItemRepository.findAllByRecipeBookIdWithRecipe(recipeBookId, pageable);
        }
        return switch (sortType) {
            case RECENT -> recipeBookItemRepository.findAllByRecipeBookIdWithRecipe(recipeBookId,
                    pageable);
            case OLDEST -> recipeBookItemRepository.findAllByRecipeBookIdWithRecipeOldest(
                    recipeBookId, pageable);
            case BOOKMARK_DESC ->
                    recipeBookItemRepository.findAllByRecipeBookIdWithRecipeByBookmarkDesc(
                            recipeBookId, pageable);
        };
    }
}
