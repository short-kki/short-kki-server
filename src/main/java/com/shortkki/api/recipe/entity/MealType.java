package com.shortkki.api.recipe.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MealType {

    MAIN("주메뉴"),
    SIDE_DISH("사이드"),
    SNACK("간식"),
    DESSERT("디저트"),
    SIDE_FOR_DRINK("안주"),
    ETC("기타");

    private final String displayName;

}
