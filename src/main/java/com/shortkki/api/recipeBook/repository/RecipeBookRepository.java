package com.shortkki.api.recipeBook.repository;

import com.shortkki.api.recipeBook.entity.RecipeBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookRepository extends JpaRepository<RecipeBook, Long>,
        RecipeBookRepositoryCustom {

    List<RecipeBook> findAllByGroupId(Long groupId);

    void deleteByGroupId(Long groupId);
}
