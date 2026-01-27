package com.shortkki.api.recipeBook.entity;


import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Entity;
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
@Table(name = "book_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeBookItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id")
    RecipeBook recipeBook;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    Recipe recipe;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Builder
    private RecipeBookItem(RecipeBook recipeBook, Recipe recipe) {
        this.recipeBook = recipeBook;
        this.recipe = recipe;
    }

    public static RecipeBookItem create(RecipeBook recipeBook, Recipe recipe) {
        return RecipeBookItem.builder()
                .recipeBook(recipeBook)
                .recipe(recipe)
                .build();
    }


}
