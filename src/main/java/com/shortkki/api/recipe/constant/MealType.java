package com.shortkki.api.recipe.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MealType {
    
    MAIN("밥"),
    SIDE_DISH("반찬"),
    SNACK("간식"),
    DESSERT("디저트"),
    SIDE_FOR_DRINK("안주");

    private final String displayName;

}
