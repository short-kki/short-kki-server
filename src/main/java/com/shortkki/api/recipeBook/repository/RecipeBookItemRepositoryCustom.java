package com.shortkki.api.recipeBook.repository;

public interface RecipeBookItemRepositoryCustom {

    /**
     * 특정 사용자가 특정 레시피를 이미 다른 레시피북에 담고 있는지 확인합니다. (현재 추가/삭제하려는 레시피북은 제외하고 검사)
     *
     * @param memberId      사용자 ID
     * @param recipeId      레시피 ID
     * @param excludeBookId 제외할 레시피북 ID (현재 작업 중인 레시피북)
     * @return 다른 레시피북에 존재하면 true, 없으면 false
     */
    boolean existsByMemberAndRecipeExcludingBook(Long memberId, Long recipeId, Long excludeBookId);

    /**
     * 특정 사용자가 특정 레시피를 어딘가(어떤 레시피북이든)에 하나라도 담고 있는지 확인합니다.
     *
     * @param memberId 사용자 ID
     * @param recipeId 레시피 ID
     * @return 하나라도 존재하면 true, 없으면 false
     */
    boolean existsByMemberAndRecipe(Long memberId, Long recipeId);

    /**
     * 특정 그룹이 특정 레시피를 이미 다른 레시피북에 담고 있는지 확인합니다. (현재 추가/삭제하려는 레시피북은 제외하고 검사)
     *
     * @param groupId       그룹 ID
     * @param recipeId      레시피 ID
     * @param excludeBookId 제외할 레시피북 ID (현재 작업 중인 레시피북)
     * @return 다른 레시피북에 존재하면 true, 없으면 false
     */
    boolean existsByGroupAndRecipeExcludingBook(Long groupId, Long recipeId, Long excludeBookId);

    /**
     * 특정 그룹이 특정 레시피를 어딘가(어떤 레시피북이든)에 하나라도 담고 있는지 확인합니다.
     *
     * @param groupId  그룹 ID
     * @param recipeId 레시피 ID
     * @return 하나라도 존재하면 true, 없으면 false
     */
    boolean existsByGroupAndRecipe(Long groupId, Long recipeId);
}
