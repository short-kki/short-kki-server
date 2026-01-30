package com.shortkki.api.shopping_list.dto.response;

import com.shortkki.api.shopping_list.entity.ShoppingList;

import java.time.LocalDateTime;

public record ShoppingListResponse(
        Long id,
        String name,
        Long ingredientId,
        LocalDateTime createdAt
) {

    public static ShoppingListResponse from(ShoppingList shoppingList) {
        return new ShoppingListResponse(
                shoppingList.getId(),
                shoppingList.getName(),
                shoppingList.getIngredient() != null ? shoppingList.getIngredient().getId() : null,
                shoppingList.getCreatedAt()
        );
    }
}
