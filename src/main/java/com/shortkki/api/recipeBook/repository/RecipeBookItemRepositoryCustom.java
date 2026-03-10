package com.shortkki.api.recipeBook.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RecipeBookItemRepositoryCustom {

    List<Long> findOwnedRecipeBookIdsByRecipeIdAndMemberId(Long recipeId, Long memberId);

    Set<Long> findBookmarkedRecipeIdsByMemberAndRecipeIds(Long memberId, List<Long> recipeIds);

    boolean existsByMemberAndRecipeExcludingBook(Long memberId, Long recipeId, Long excludeBookId);

    boolean existsByGroupAndRecipeExcludingBook(Long groupId, Long recipeId, Long excludeBookId);

    List<Long> findRecipeIdsBookmarkedByMemberExcludingBook(Long memberId, List<Long> recipeIds,
            Long excludeBookId);

    Map<Long, Long> countByRecipeBookIds(List<Long> recipeBookIds);

    List<Long> findRecipeIdsBookmarkedElsewhere(Long groupId, List<Long> recipeIds);
}
