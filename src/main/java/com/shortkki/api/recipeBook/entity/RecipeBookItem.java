package com.shortkki.api.recipeBook.entity;


import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;


@Entity
// TODO : 나중에 DB 유니크 제약조건으로 변경 (데이터 정합성을 위해서)
@Table(name = "recipe_book_item",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"recipe_book_id", "recipe_id"})
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeBookItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private RecipeBook recipeBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

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
