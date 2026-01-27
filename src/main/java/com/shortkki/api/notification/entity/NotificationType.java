package com.shortkki.api.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    GROUP_INVITE("그룹 초대"),
    RECIPE_SHARED("레시피 공유"),
    CALENDAR_UPDATE("식단 등록"),
    COMMENT_ADDED("댓글 알림");

    private final String description;
}
