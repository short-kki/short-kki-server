package com.shortkki.api.curation.entity.converter;

import com.shortkki.api.recipe.entity.CuisineType;
import jakarta.persistence.Converter;

@Converter
public class CuisineTypeSetConverter extends CurationEnumSetConverter<CuisineType> {

    public CuisineTypeSetConverter() {
        super(CuisineType.class);
    }
}
