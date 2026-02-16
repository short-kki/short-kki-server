package com.shortkki.api.notification.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortkki.api.notification.entity.NotificationType;
import java.util.List;
import java.util.Map;

public record NotificationEvent(
        List<Long> receiverIds,
        Long senderId,
        NotificationType type,
        String content,
        String relatedUrl,
        Long targetId,
        String payload
) {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static NotificationEvent groupInvite(Long receiverId, Long senderId, Long groupId, String groupName) {
        return new NotificationEvent(
                List.of(receiverId),
                senderId,
                NotificationType.GROUP_INVITE,
                groupName + " 그룹에 초대되었습니다.",
                "/groups/" + groupId,
                groupId,
                toJson(Map.of("groupId", String.valueOf(groupId)))
        );
    }

    public static NotificationEvent memberJoined(List<Long> receiverIds, Long joinedMemberId, Long groupId, String memberName) {
        return new NotificationEvent(
                receiverIds,
                joinedMemberId,
                NotificationType.GROUP_MEMBER_JOINED,
                memberName + "님이 그룹에 참여했습니다.",
                "/groups/" + groupId,
                groupId,
                toJson(Map.of("groupId", String.valueOf(groupId)))
        );
    }

    public static NotificationEvent recipeShared(List<Long> receiverIds, Long senderId, Long recipeId, String recipeName, Long groupId) {
        return new NotificationEvent(
                receiverIds,
                senderId,
                NotificationType.RECIPE_SHARED,
                recipeName + " 레시피가 공유되었습니다.",
                "/groups/" + groupId + "/recipes/" + recipeId,
                recipeId,
                toJson(Map.of(
                        "groupId", String.valueOf(groupId),
                        "recipeId", String.valueOf(recipeId)
                ))
        );
    }

    public static NotificationEvent calendarUpdate(List<Long> receiverIds, Long senderId, Long calendarId, String date, Long groupId) {
        return new NotificationEvent(
                receiverIds,
                senderId,
                NotificationType.CALENDAR_UPDATE,
                date + " 식단이 등록되었습니다.",
                "/calendars/" + calendarId,
                calendarId,
                toJson(Map.of(
                        "groupId", String.valueOf(groupId),
                        "calendarId", String.valueOf(calendarId),
                        "date", date
                ))
        );
    }

    public static NotificationEvent commentAdded(Long receiverId, Long senderId, Long recipeId, String senderName) {
        return new NotificationEvent(
                List.of(receiverId),
                senderId,
                NotificationType.COMMENT_ADDED,
                senderName + "님이 댓글을 남겼습니다.",
                "/recipes/" + recipeId,
                recipeId,
                toJson(Map.of("recipeId", String.valueOf(recipeId)))
        );
    }

    public static NotificationEvent single(Long receiverId, Long senderId, NotificationType type,
            String content, String relatedUrl, Long targetId, Map<String, String> payload) {
        return new NotificationEvent(List.of(receiverId), senderId, type, content, relatedUrl, targetId, toJson(payload));
    }

    public static NotificationEvent multiple(List<Long> receiverIds, Long senderId, NotificationType type,
            String content, String relatedUrl, Long targetId, Map<String, String> payload) {
        return new NotificationEvent(receiverIds, senderId, type, content, relatedUrl, targetId, toJson(payload));
    }

    private static String toJson(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
