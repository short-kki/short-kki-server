package com.shortkki.api.curation.entity;

import java.time.DayOfWeek;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayType {

    WEEKDAY("평일"),
    WEEKEND("주말");

    private final String displayName;

    public static DayType from(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case SATURDAY, SUNDAY -> WEEKEND;
            default -> WEEKDAY;
        };
    }
}
