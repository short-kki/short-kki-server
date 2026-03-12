package com.shortkki.api.recipeImport.adapter;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.IngredientParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.StepParseResult;
import com.shortkki.api.recipeImport.port.RecipeParserPort;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("perf")
public class PerfRecipeParserAdapter implements RecipeParserPort {

    private static final long PARSER_DELAY_MILLIS = 10_000L;

    @Override
    public RecipeParseResult parseRecipeFromUrl(String url, List<String> officialTagNames) {
        try {
            Thread.sleep(PARSER_DELAY_MILLIS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new RecipeParseResult(
                "perf-import-recipe",
                "perf profile parser response",
                1,
                10,
                CuisineType.ETC,
                MealType.ETC,
                Difficulty.ETC,
                List.of(new IngredientParseResult("water", 1.0, "cup")),
                List.of(new StepParseResult(1, "boil")),
                List.of("perf-tag"),
                "perf-fake-raw-response");
    }
}
