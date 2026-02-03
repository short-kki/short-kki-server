package com.shortkki.api.shopping_list.repository;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.shopping_list.entity.ShoppingList;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>,
        ShoppingListRepositoryCustom {

    List<ShoppingList> findByGroupId(Long groupId);

    @Query("SELECT sl.ingredient.id FROM ShoppingList sl WHERE sl.group.id = :groupId")
    Set<Long> findIngredientIdsByGroupId(@Param("groupId") Long groupId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ShoppingList sl WHERE sl.group = :group")
    void deleteAllByGroup(@Param("group") Group group);
}
