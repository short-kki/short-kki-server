package com.shortkki.api.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    GROUP_MEMBER_JOINED("새 멤버 가입"),
    RECIPE_SHARED("레시피 공유"),
    CALENDAR_UPDATE("식단 등록"),
    FEED_ADDED("그룹 피드 생성");

    private final String description;
}
