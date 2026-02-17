package com.shortkki.api.recipe.repository;

import com.shortkki.api.recipe.entity.Recipe;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("""
            SELECT r FROM Recipe r
            LEFT JOIN FETCH r.member
            LEFT JOIN FETCH r.sourceContent sc
            LEFT JOIN FETCH sc.sourceCreator
            LEFT JOIN FETCH r.mainImgFile
            WHERE r.id IN :ids AND r.isActive = true
            """)
    List<Recipe> findActiveByIdsWithAssociations(@Param("ids") List<Long> ids);

    @Modifying
    @Query("""
            UPDATE Recipe r
            SET r.bookmarkCount = r.bookmarkCount + 1
            WHERE r.id = :recipeId
            """)
    void incrementBookmarkCount(@Param("recipeId") Long recipeId);

    @Modifying
    @Query("""
            UPDATE Recipe r
            SET r.bookmarkCount = r.bookmarkCount - 1
            WHERE r.id = :recipeId AND r.bookmarkCount > 0
            """)
    void decrementBookmarkCount(@Param("recipeId") Long recipeId);

    @Modifying
    @Query("""
            UPDATE Recipe r
            SET r.bookmarkCount = r.bookmarkCount - 1
            WHERE r.id IN :recipeIds AND r.bookmarkCount > 0
            """)
    void decrementBookmarkCountBulk(@Param("recipeIds") List<Long> recipeIds);

    boolean existsBySourceContentId(Long sourceContentId);

    @Query("""
            SELECT r FROM Recipe r
            WHERE (:fromId IS NULL OR r.id >= :fromId)
              AND (:fromCreatedAt IS NULL OR r.createdAt >= :fromCreatedAt)
            ORDER BY r.id ASC
            """)
    Slice<Recipe> findAllFrom(@Param("fromId") Long fromId, @Param("fromCreatedAt") LocalDateTime fromCreatedAt, Pageable pageable);
}
