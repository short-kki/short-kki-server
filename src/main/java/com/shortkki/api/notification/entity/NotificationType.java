package com.shortkki.api.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    GROUP_INVITE("그룹 초대"),
    GROUP_MEMBER_JOINED("새 멤버 가입"),
    RECIPE_SHARED("레시피 공유"),
    RECIPE_IMPORT_COMPLETED("외부 레시피 완료"),
    CALENDAR_UPDATE("식단 등록"),
    COMMENT_ADDED("댓글 알림"),
    FEED_ADDED("그룹 피드 생성");
    private final String description;
}
