package com.shortkki.api.recipeBook.service;

import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeBookBookmarkPolicy {

    private final RecipeBookItemRepository recipeBookItemRepository;
    private final RecipeRepository recipeRepository;

    public List<Long> decreaseBookmarkCountsForDeletedBook(RecipeBook recipeBook,
            List<RecipeBookItem> items) {
        if (items.isEmpty()) {
            return List.of();
        }

        List<Long> recipeIds = items.stream()
                .map(item -> item.getRecipe().getId())
                .toList();

        if (recipeIds.isEmpty()) {
            return List.of();
        }

        List<Long> otherBookmarkedIds = findBookmarkedRecipeIdsInOtherBooks(recipeBook, recipeIds);
        Set<Long> otherBookmarkedIdSet = new HashSet<>(otherBookmarkedIds);

        List<Long> recipeIdsToDecrement = recipeIds.stream()
                .filter(id -> !otherBookmarkedIdSet.contains(id))
                .toList();

        if (!recipeIdsToDecrement.isEmpty()) {
            recipeRepository.decrementBookmarkCountBulk(recipeIdsToDecrement);
        }

        return recipeIdsToDecrement;
    }

    public void increaseBookmarkCountIfFirstOwnership(RecipeBook recipeBook, Long recipeId,
            Long currentBookId) {
        if (hasOtherBookmarks(recipeBook, recipeId, currentBookId)) {
            return;
        }
        recipeRepository.incrementBookmarkCount(recipeId);
    }

    public void decreaseBookmarkCountIfLastOwnership(RecipeBook recipeBook, Long recipeId,
            Long currentBookId, long deletedRows) {
        if (deletedRows <= 0) {
            return;
        }
        if (!hasOtherBookmarks(recipeBook, recipeId, currentBookId)) {
            recipeRepository.decrementBookmarkCount(recipeId);
        }
    }

    private boolean hasOtherBookmarks(RecipeBook recipeBook, Long recipeId, Long excludeBookId) {
        if (recipeBook.getMemberId() != null) {
            return recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(
                    recipeBook.getMemberId(), recipeId, excludeBookId);
        }
        return recipeBookItemRepository.existsByGroupAndRecipeExcludingBook(
                recipeBook.getGroupId(), recipeId, excludeBookId);
    }

    private List<Long> findBookmarkedRecipeIdsInOtherBooks(RecipeBook recipeBook,
            List<Long> recipeIds) {
        if (recipeBook.getMemberId() == null) {
            throw new BadRequestException(ErrorCode.CANNOT_DELETE_GROUP_RECIPE_BOOK);
        }
        return recipeBookItemRepository.findRecipeIdsBookmarkedByMemberExcludingBook(
                recipeBook.getMemberId(), recipeIds, recipeBook.getId());
    }
}
