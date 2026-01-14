package com.example.short_kki.domain.recipe.service;

import com.example.short_kki.domain.recipe.dto.RecipeCreateRequest;
import com.example.short_kki.domain.recipe.dto.RecipeResponse;
import com.example.short_kki.domain.recipe.entity.Recipe;
import com.example.short_kki.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;

    /**
     * 레시피 생성
     */
    @Transactional
    public RecipeResponse create(RecipeCreateRequest request) {
        Recipe recipe = request.toEntity();
        Recipe saved = recipeRepository.save(recipe);
        return RecipeResponse.from(saved);
    }

    /**
     * 레시피 단건 조회
     */
    public RecipeResponse findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다. id=" + id));
        return RecipeResponse.from(recipe);
    }

    /**
     * 레시피 전체 조회
     */
    public List<RecipeResponse> findAll() {
        return recipeRepository.findAll().stream()
                .map(RecipeResponse::from)
                .toList();
    }

    /**
     * 레시피 삭제
     */
    @Transactional
    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다. id=" + id));
        recipeRepository.delete(recipe);
    }
}
