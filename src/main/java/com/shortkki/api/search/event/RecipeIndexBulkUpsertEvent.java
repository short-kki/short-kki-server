package com.shortkki.api.search.event;

import java.util.List;

public record RecipeIndexBulkUpsertEvent(
        List<Long> recipeIds
) {

}
