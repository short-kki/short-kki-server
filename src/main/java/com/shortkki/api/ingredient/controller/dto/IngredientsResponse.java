package com.shortkki.api.ingredient.controller.dto;

import java.util.List;

public record IngredientsResponse(
        List<IngredientResponse> ingredients,
        int total
) {

}
