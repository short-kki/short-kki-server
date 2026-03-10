package com.shortkki.api.notification.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortkki.api.notification.entity.NotificationType;
import java.util.List;
import java.util.Map;

public record NotificationEvent(
        List<Long> receiverIds,
        NotificationType type,
        String content,
        Long targetId,
        String payload
) {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static NotificationEvent groupInvite(Long receiverId, Long groupId, String groupName) {
        return new NotificationEvent(
                List.of(receiverId),
                NotificationType.GROUP_INVITE,
                groupName + " 그룹에 초대되었습니다.",
                groupId,
                toJson(Map.of("groupId", String.valueOf(groupId)))
        );
    }

    public static NotificationEvent memberJoined(List<Long> receiverIds, Long groupId,
            String memberName) {
        return new NotificationEvent(
                receiverIds,
                NotificationType.GROUP_MEMBER_JOINED,
                memberName + "님이 그룹에 참여했습니다.",
                groupId,
                toJson(Map.of("groupId", String.valueOf(groupId)))
        );
    }

    public static NotificationEvent recipeShared(List<Long> receiverIds, Long recipeId,
            String recipeName, Long groupId) {
        return new NotificationEvent(
                receiverIds,
                NotificationType.RECIPE_SHARED,
                recipeName + " 레시피가 공유되었습니다.",
                recipeId,
                toJson(Map.of(
                        "groupId", String.valueOf(groupId),
                        "recipeId", String.valueOf(recipeId)
                ))
        );
    }

    public static NotificationEvent calendarUpdate(List<Long> receiverIds, Long calendarId,
            String date, Long groupId) {
        return new NotificationEvent(
                receiverIds,
                NotificationType.CALENDAR_UPDATE,
                date + " 식단이 등록되었습니다.",
                calendarId,
                toJson(Map.of(
                        "groupId", String.valueOf(groupId),
                        "calendarId", String.valueOf(calendarId),
                        "date", date
                ))
        );
    }

    public static NotificationEvent commentAdded(Long receiverId, Long recipeId,
            String senderName) {
        return new NotificationEvent(
                List.of(receiverId),
                NotificationType.COMMENT_ADDED,
                senderName + "님이 댓글을 남겼습니다.",
                recipeId,
                toJson(Map.of("recipeId", String.valueOf(recipeId)))
        );
    }

    public static NotificationEvent feedAdded(List<Long> receiverIds, Long feedId,
            String memberName, Long groupId) {
        return new NotificationEvent(
                receiverIds,
                NotificationType.FEED_ADDED,
                memberName + "님이 새 피드를 작성했습니다.",
                feedId,
                toJson(Map.of(
                        "groupId", String.valueOf(groupId),
                        "feedId", String.valueOf(feedId)
                ))
        );
    }
    
    public static NotificationEvent importedRecipeCompleted(Long receiverId, Long recipeId, String recipeTitle) {    
        return new NotificationEvent(
                List.of(receiverId),
                NotificationType.RECIPE_IMPORT_COMPLETED,
                "'" + recipeTitle + "' 레시피가 저장되었어요.",
                recipeId,
                toJson(Map.of("recipeId", String.valueOf(recipeId)))
        );
    }

    public static NotificationEvent single(Long receiverId, NotificationType type,
            String content, Long targetId, Map<String, String> payload) {
        return new NotificationEvent(List.of(receiverId), type, content, targetId, toJson(payload));
    }

    public static NotificationEvent multiple(List<Long> receiverIds, NotificationType type,
            String content, Long targetId, Map<String, String> payload) {
        return new NotificationEvent(receiverIds, type, content, targetId, toJson(payload));
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
