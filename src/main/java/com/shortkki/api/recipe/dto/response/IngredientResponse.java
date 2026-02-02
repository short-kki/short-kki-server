package com.shortkki.api.recipe.dto.response;

public record IngredientResponse(
        String name,
        String unit,
        Double amount
) {

}
