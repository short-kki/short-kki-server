package com.shortkki.api.shopping_list.repository;

import com.shortkki.api.shopping_list.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>, ShoppingListRepositoryCustom {

}
