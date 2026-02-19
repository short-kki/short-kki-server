package com.shortkki.api.search.application.port;

import com.shortkki.api.recipe.entity.Recipe;
import java.util.List;

public interface RecipeSearchIndexer {
    void upsert(long recipeId);

    void upsertAll(List<Recipe> recipes);

    void delete(long recipeId);
}