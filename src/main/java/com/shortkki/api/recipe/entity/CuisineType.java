package com.shortkki.api.recipe.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
