package com.shortkki.api.recipeBook.repository;

import java.util.List;
import java.util.Map;

public interface RecipeBookItemRepositoryCustom {

    List<Long> findOwnedRecipeBookIdsByRecipeIdAndMemberId(Long recipeId, Long memberId);

    boolean existsByMemberAndRecipeExcludingBook(Long memberId, Long recipeId, Long excludeBookId);

    boolean existsByGroupAndRecipeExcludingBook(Long groupId, Long recipeId, Long excludeBookId);

    List<Long> findRecipeIdsBookmarkedByMemberExcludingBook(Long memberId, List<Long> recipeIds,
            Long excludeBookId);

    Map<Long, Long> countByRecipeBookIds(List<Long> recipeBookIds);
}
