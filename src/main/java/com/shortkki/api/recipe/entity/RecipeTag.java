package com.shortkki.api.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_tag",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"recipe_id", "tag_id"})
        })
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long recipeId;

    @Column(nullable = false)
    private Long tagId;

    public static RecipeTag of(long recipeId, long tagId) {
        return RecipeTag.builder()
                .recipeId(recipeId)
                .tagId(tagId)
                .build();
    }
}
