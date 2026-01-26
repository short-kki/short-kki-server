package com.shortkki.api.shopping_list.repository;

import com.shortkki.api.shopping_list.entity.ShoppingList;

import java.util.List;

public interface ShoppingListRepositoryCustom {

    List<ShoppingList> findByGroupId(Long groupId);
}
