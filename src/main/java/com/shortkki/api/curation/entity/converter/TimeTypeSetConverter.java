package com.shortkki.api.curation.entity.converter;

import com.shortkki.api.curation.entity.TimeType;
import jakarta.persistence.Converter;

@Converter
public class TimeTypeSetConverter extends CurationEnumSetConverter<TimeType> {

    public TimeTypeSetConverter() {
        super(TimeType.class);
    }
}
