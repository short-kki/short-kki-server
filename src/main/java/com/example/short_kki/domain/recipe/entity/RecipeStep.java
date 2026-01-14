package com.example.short_kki.domain.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_step")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -- 설정 고려하지 않고 나중에 테스트해서 변경 예정
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    private Integer sequence;

    @Column(length = 200)
    private String description;

    @Builder
    private RecipeStep(Integer sequence, String description) {
        this.sequence = sequence;
        this.description = description;
    }

    public static RecipeStep create(Integer sequence, String description) {
        return RecipeStep.builder()
                .sequence(sequence)
                .description(description)
                .build();
    }
}
