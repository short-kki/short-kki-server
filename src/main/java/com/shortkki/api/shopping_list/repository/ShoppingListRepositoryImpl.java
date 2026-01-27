package com.shortkki.api.shopping_list.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.shopping_list.entity.QShoppingList;
import com.shortkki.api.shopping_list.entity.ShoppingList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShoppingListRepositoryImpl implements ShoppingListRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QShoppingList shoppingList = QShoppingList.shoppingList;

}
