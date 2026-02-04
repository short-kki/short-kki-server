package com.shortkki.api.recipeImport.port;

import com.shortkki.api.recipeImport.dto.RecipeParseResult;

public interface RecipeParserPort {
    RecipeParseResult parseRecipeFromUrl(String url);
}
