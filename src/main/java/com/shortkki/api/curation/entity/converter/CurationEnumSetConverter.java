package com.shortkki.api.curation.entity.converter;

import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CurationEnumSetConverter<E extends Enum<E>> implements AttributeConverter<Set<E>, String> {

    private static final String DELIMITER = ",";

    private final Class<E> enumClass;

    protected CurationEnumSetConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(Set<E> attribute) {
        if (attribute == null || attribute.isEmpty()) return "";
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<E> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return Collections.emptySet();

        EnumSet<E> result = EnumSet.noneOf(enumClass);

        Arrays.stream(dbData.split(DELIMITER, -1))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .forEach(token -> result.add(Enum.valueOf(enumClass, token)));

        return result;
    }
}
