package com.shortkki.api.curation.entity.converter;

import com.shortkki.api.curation.entity.DayType;
import jakarta.persistence.Converter;

@Converter
public class DayTypeSetConverter extends CurationEnumSetConverter<DayType> {

    public DayTypeSetConverter() {
        super(DayType.class);
    }
}
