package com.example.short_kki.domain.recipe.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    BEGINNER("초급"), INTERMEDIATE("중급"), ADVANCED("고급");

    private final String displayName;
}
