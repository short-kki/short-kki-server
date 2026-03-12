package com.shortkki.api.recipeBook.repository;

import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeBookItemRepository
        extends JpaRepository<RecipeBookItem, Long>, RecipeBookItemRepositoryCustom
{
  List<RecipeBookItem> findAllByRecipeBookId(Long recipeBookId);

  long countByRecipeBookId(Long recipeBookId);

  boolean existsByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

  long deleteByRecipeBookIdAndRecipeId(Long recipeBookId, Long recipeId);

  long deleteAllByRecipeBookId(Long recipeBookId);

  @Query("""
      select i from RecipeBookItem i
      join fetch i.recipe
      where i.recipeBook.id = :recipeBookId
      order by i.createdAt desc, i.id desc
      """)
  Slice<RecipeBookItem> findAllByRecipeBookIdWithRecipe(
      @Param("recipeBookId") Long recipeBookId, Pageable pageable);

  @Query("""
      select i from RecipeBookItem i
      join fetch i.recipe
      where i.recipeBook.id = :recipeBookId
      order by i.createdAt asc, i.id asc
      """)
  Slice<RecipeBookItem> findAllByRecipeBookIdWithRecipeOldest(
      @Param("recipeBookId") Long recipeBookId, Pageable pageable);

  @Query("""
      select i from RecipeBookItem i
      join fetch i.recipe
      where i.recipeBook.id = :recipeBookId
      order by i.recipe.bookmarkCount desc, i.createdAt desc, i.id desc
      """)
  Slice<RecipeBookItem> findAllByRecipeBookIdWithRecipeByBookmarkDesc(
      @Param("recipeBookId") Long recipeBookId, Pageable pageable);

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

  @Query("""
      SELECT DISTINCT rbi.recipe.id
        FROM RecipeBookItem rbi
       WHERE rbi.recipeBook.id IN :recipeBookIds
      """)
  List<Long> findRecipeIdsByRecipeBookIds(@Param("recipeBookIds") List<Long> recipeBookIds);

  @Modifying
  @Query("""
      DELETE FROM RecipeBookItem rbi
       WHERE rbi.recipeBook.id IN :recipeBookIds
      """)
  void deleteAllByRecipeBookIds(@Param("recipeBookIds") List<Long> recipeBookIds);

  @Query("""
      select i from RecipeBookItem i
      join fetch i.recipe
      where i.recipeBook.id in :recipeBookIds
        and (
          select count(i2) from RecipeBookItem i2
          where i2.recipeBook.id = i.recipeBook.id
            and (
              i2.createdAt > i.createdAt
              or (i2.createdAt = i.createdAt and i2.id >= i.id)
            )
        ) <= :previewLimit
      order by i.recipeBook.id asc, i.createdAt desc, i.id desc
      """)
  List<RecipeBookItem> findPreviewItemsByRecipeBookIds(
      @Param("recipeBookIds") List<Long> recipeBookIds,
      @Param("previewLimit") long previewLimit);
}
