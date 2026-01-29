package com.shortkki.api.recipeBook.repository;

import com.shortkki.api.recipeBook.entity.RecipeBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookRepository extends JpaRepository<RecipeBook, Long>,
        RecipeBookRepositoryCustom {

    void deleteByGroupId(Long groupId);
}
