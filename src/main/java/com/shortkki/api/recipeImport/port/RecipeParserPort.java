package com.shortkki.api.recipeImport.port;

import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import java.util.List;

public interface RecipeParserPort {
    RecipeParseResult parseRecipeFromUrl(String url, List<String> officialTagNames);
}
