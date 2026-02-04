package com.shortkki.api.recipe.service;

import com.shortkki.api.recipe.entity.Tag;
import com.shortkki.api.recipe.repository.RecipeTagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeTagQueryService {

    private final RecipeTagRepository recipeTagRepository;

    public List<Tag> findTagsByRecipeId(Long recipeId) {
        return recipeTagRepository.findTagsByRecipeId(recipeId);
    }
}
