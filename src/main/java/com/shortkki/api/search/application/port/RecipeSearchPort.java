package com.shortkki.api.search.application.port;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RecipeSearchPort {

    Slice<RecipeSearchItem> search(
            Pageable pageable, String searchWord,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    );
}
