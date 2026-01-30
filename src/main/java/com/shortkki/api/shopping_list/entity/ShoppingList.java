package com.shortkki.api.shopping_list.entity;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shopping_list")
public class ShoppingList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Builder
    private ShoppingList(String name, Ingredient ingredient, Group group) {
        this.name = name;
        this.ingredient = ingredient;
        this.group = group;
    }

    public static ShoppingList create(String name, Ingredient ingredient, Group group) {
        return ShoppingList.builder()
                .name(name)
                .ingredient(ingredient)
                .group(group)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
