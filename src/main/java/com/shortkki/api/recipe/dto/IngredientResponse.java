package com.shortkki.api.recipe.dto;

public record IngredientResponse(
        String name,
        String unit,
        Double amount
) {

}
