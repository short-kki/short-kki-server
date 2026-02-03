package com.shortkki.api.search.application.port;

public interface RecipeSearchIndexer {
    void upsert(long recipeId);
}