package com.shortkki.api.recipeBook.repository;

import java.util.List;

public interface RecipeBookItemRepositoryCustom {

    boolean existsByMemberAndRecipeExcludingBook(Long memberId, Long recipeId, Long excludeBookId);

    boolean existsByMemberAndRecipe(Long memberId, Long recipeId);

    boolean existsByGroupAndRecipeExcludingBook(Long groupId, Long recipeId, Long excludeBookId);

    boolean existsByGroupAndRecipe(Long groupId, Long recipeId);

    List<Long> findRecipeIdsBookmarkedByMemberExcludingBook(Long memberId, List<Long> recipeIds, Long excludeBookId);

    List<Long> findRecipeIdsBookmarkedByGroupExcludingBook(Long groupId, List<Long> recipeIds, Long excludeBookId);
}
