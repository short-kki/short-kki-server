package com.shortkki.api.recipe.service;


import com.shortkki.api.ingredient.controller.dto.IngredientResponse;
import com.shortkki.api.ingredient.controller.dto.IngredientsResponse;
import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// TODO : 엘라스틱 서치 서비스로 대체하면 좋을 것 같다.
public class IngredientQueryService {

    private final IngredientRepository ingredientRepository;

    public Ingredient findStandardByNameOrNull(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return ingredientRepository.findByName(name.trim()).orElse(null);
    }

    public IngredientsResponse getAll() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientResponse> items = ingredients.stream()
                .map(i -> new IngredientResponse(i.getId(), i.getName()))
                .toList();

        return new IngredientsResponse(items, ingredients.size());
    }
}
