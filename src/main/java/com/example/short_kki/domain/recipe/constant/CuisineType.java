package com.example.short_kki.domain.recipe.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 수정 필요
@Getter
@RequiredArgsConstructor

public enum CuisineType {
    KOREAN("한식"),
    WESTERN("양식"),
    JAPANESE("일식"),
    CHINESE("중식"),
    ASIAN("아시아"),
    FUSION("퓨전");

    private final String displayName;
}
