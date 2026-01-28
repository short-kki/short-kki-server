package com.shortkki.api.recipeBook.repository;

import com.shortkki.api.recipeBook.entity.RecipeBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookRepository extends JpaRepository<RecipeBook, Long>,
        RecipeBookRepositoryCustom {

    void deleteAllByMember_Id(Long memberId);

    void deleteByGroupId(Long groupId);
}
