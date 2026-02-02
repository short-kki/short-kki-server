package com.shortkki.api.curation.entity.converter;

import com.shortkki.api.recipe.entity.MealType;
import jakarta.persistence.Converter;

@Converter
public class MealTypeSetConverter extends CurationEnumSetConverter<MealType> {

    public MealTypeSetConverter() {
        super(MealType.class);
    }
}
