package com.shortkki.api.curation.entity.converter;

import com.shortkki.api.recipe.entity.Difficulty;
import jakarta.persistence.Converter;

@Converter
public class DifficultySetConverter extends CurationEnumSetConverter<Difficulty> {

    public DifficultySetConverter() {
        super(Difficulty.class);
    }
}