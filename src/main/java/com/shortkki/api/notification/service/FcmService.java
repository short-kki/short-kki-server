package com.shortkki.api.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.shortkki.api.device.entity.DeviceToken;
import com.shortkki.api.device.repository.DeviceTokenRepository;
import com.shortkki.api.notification.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final DeviceTokenRepository deviceTokenRepository;

    public void sendPush(Long receiverId, NotificationType type, String content, String relatedUrl) {
        if (firebaseMessaging == null) {
            log.debug("FirebaseMessaging is not initialized. Skipping push notification.");
            return;
        }

        List<DeviceToken> deviceTokens = deviceTokenRepository.findByMemberId(receiverId);

        if (deviceTokens.isEmpty()) {
            log.debug("No device tokens found for memberId: {}", receiverId);
            return;
        }

        String title = getTitleByType(type);

        for (DeviceToken deviceToken : deviceTokens) {
            sendToDevice(deviceToken.getToken(), title, content, relatedUrl);
        }
    }

    private void sendToDevice(String token, String title, String body, String relatedUrl) {
        Message.Builder messageBuilder = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build());

        if (relatedUrl != null) {
            messageBuilder.putData("relatedUrl", relatedUrl);
        }

        try {
            String response = firebaseMessaging.send(messageBuilder.build());
            log.debug("FCM message sent successfully: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send FCM message to token {}: {}", token, e.getMessage());
            handleFailedToken(token, e);
        }
    }

    private String getTitleByType(NotificationType type) {
        return switch (type) {
            case GROUP_INVITE -> "그룹 초대";
            case RECIPE_SHARED -> "레시피 공유";
            case CALENDAR_UPDATE -> "식단 등록";
            case COMMENT_ADDED -> "새 댓글";
        };
    }

    private void handleFailedToken(String token, FirebaseMessagingException e) {
        String errorCode = e.getMessagingErrorCode() != null
                ? e.getMessagingErrorCode().name()
                : "UNKNOWN";

        if ("UNREGISTERED".equals(errorCode) || "INVALID_ARGUMENT".equals(errorCode)) {
            log.info("Removing invalid FCM token: {}", token);
            deviceTokenRepository.deleteByToken(token);
        }
    }
}
