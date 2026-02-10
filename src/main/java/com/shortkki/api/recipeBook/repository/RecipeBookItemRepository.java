package com.shortkki.api.recipeBook.repository;

import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeBookItemRepository extends JpaRepository<RecipeBookItem, Long>,
    RecipeBookItemRepositoryCustom {

  List<RecipeBookItem> findAllByRecipeBookId(Long recipeBookId);

  boolean existsByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

  long deleteByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

  long deleteAllByRecipeBookId(Long recipeBookId);

  @Query("""
      select i from RecipeBookItem i
      join fetch i.recipe
      where i.recipeBook.id = :recipeBookId
      order by i.createdAt desc, i.id desc
      """)
  List<RecipeBookItem> findAllByRecipeBookIdWithRecipe(@Param("recipeBookId") Long recipeBookId);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("""
          update RecipeBookItem rbi
             set rbi.recipeBook = :toBook
           where rbi.recipeBook = :fromBook
             and rbi.recipe.id = :recipeId
      """)
  long moveRecipe(@Param("fromBook") RecipeBook fromBook,
      @Param("toBook") RecipeBook toBook,
      @Param("recipeId") Long recipeId);

  @Query("SELECT rbi.recipe.id FROM RecipeBookItem rbi WHERE rbi.recipeBook.id = :recipeBookId")
  List<Long> findRecipeIdsByRecipeBookId(@Param("recipeBookId") Long recipeBookId);

  @Query("""
      select i from RecipeBookItem i
      join fetch i.recipe
      where i.recipeBook.id in :recipeBookIds
      """)
  List<RecipeBookItem> findAllByRecipeBookIdsWithRecipe(
      @Param("recipeBookIds") List<Long> recipeBookIds);

  @Query("""
      SELECT DISTINCT rbi.recipe.id
      FROM RecipeBookItem rbi
      WHERE rbi.recipeBook.member.id = :memberId
        AND rbi.recipe.id IN :recipeIds
        AND rbi.recipeBook.id != :excludeBookId
      """)
  List<Long> findRecipeIdsBookmarkedByMemberExcludingBook(
      @Param("memberId") Long memberId,
      @Param("recipeIds") List<Long> recipeIds,
      @Param("excludeBookId") Long excludeBookId);

  @Query("""
      SELECT DISTINCT rbi.recipe.id
      FROM RecipeBookItem rbi
      WHERE rbi.recipeBook.group.id = :groupId
        AND rbi.recipe.id IN :recipeIds
        AND rbi.recipeBook.id != :excludeBookId
      """)
  List<Long> findRecipeIdsBookmarkedByGroupExcludingBook(
      @Param("groupId") Long groupId,
      @Param("recipeIds") List<Long> recipeIds,
      @Param("excludeBookId") Long excludeBookId);

  @Query("SELECT COUNT(rbi) > 0 FROM RecipeBookItem rbi WHERE rbi.recipeBook.member.id = :memberId AND rbi.recipe.id = :recipeId")
  boolean existsByMemberAndRecipe(@Param("memberId") Long memberId, @Param("recipeId") Long recipeId);

  @Query("SELECT COUNT(rbi) > 0 FROM RecipeBookItem rbi WHERE rbi.recipeBook.group.id = :groupId AND rbi.recipe.id = :recipeId")
  boolean existsByGroupAndRecipe(@Param("groupId") Long groupId, @Param("recipeId") Long recipeId);

  @Query("""
      SELECT COUNT(rbi) > 0
      FROM RecipeBookItem rbi
      WHERE rbi.recipeBook.member.id = :memberId
        AND rbi.recipe.id = :recipeId
        AND rbi.recipeBook.id != :excludeBookId
      """)
  boolean existsByMemberAndRecipeExcludingBook(@Param("memberId") Long memberId, @Param("recipeId") Long recipeId,
      @Param("excludeBookId") Long excludeBookId);

  @Query("""
      SELECT COUNT(rbi) > 0
      FROM RecipeBookItem rbi
      WHERE rbi.recipeBook.group.id = :groupId
        AND rbi.recipe.id = :recipeId
        AND rbi.recipeBook.id != :excludeBookId
      """)
  boolean existsByGroupAndRecipeExcludingBook(@Param("groupId") Long groupId, @Param("recipeId") Long recipeId,
      @Param("excludeBookId") Long excludeBookId);
}