package com.shortkki.api.publicdata.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublicDataIngredientPersistService {

    private final IngredientRepository ingredientRepository;

    @Transactional
    public int saveNewIngredients(Set<String> names) {
        List<Ingredient> existing = ingredientRepository.findAllByNameIn(names);
        Set<String> existingNames = existing.stream()
                .map(Ingredient::getName)
                .collect(Collectors.toSet());

        List<Ingredient> toSave = names.stream()
                .filter(n -> !existingNames.contains(n))
                .map(Ingredient::create)
                .toList();

        if (toSave.isEmpty()) {
            return 0;
        }

        ingredientRepository.saveAll(toSave);
        return toSave.size();
    }
}
