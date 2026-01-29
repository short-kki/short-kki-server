package com.shortkki.api.search.repository;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.Recipe;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RecipeSearchRepository {

    Slice<Recipe> search(
            Set<String> splitSearchWord, List<CuisineType> cuisineType, List<MealType> mealType, Pageable pageable
    );
}
