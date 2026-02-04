package com.shortkki.api.curation.entity;

import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeType {

    MORNING("아침", LocalTime.of(6, 0), LocalTime.of(11, 0)),
    LUNCH("점심", LocalTime.of(11, 0), LocalTime.of(16, 0)),
    DINNER("저녁", LocalTime.of(16, 0), LocalTime.of(22, 0)),
    LATE_NIGHT("야식", LocalTime.of(22, 0), LocalTime.of(6, 0));

    private final String displayName;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public boolean contains(LocalTime time) {
        if (startTime.isBefore(endTime)) {
            return !time.isBefore(startTime) && time.isBefore(endTime);
        }
        // 자정을 넘기는 경우 (LATE_NIGHT)
        return !time.isBefore(startTime) || time.isBefore(endTime);
    }

    public static TimeType from(LocalTime time) {
        for (TimeType type : values()) {
            if (type.contains(time)) {
                return type;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 시간대입니다: " + time);
    }
}
