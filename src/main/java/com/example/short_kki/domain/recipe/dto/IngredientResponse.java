package com.example.short_kki.domain.recipe.dto;

public record IngredientResponse(
        String name,
        String unit,
        Integer amount) {
}
