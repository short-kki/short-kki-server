package com.shortkki.api.notification.infra.fcm;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.shortkki.api.notification.application.dto.PushMessage;
import com.shortkki.api.notification.application.port.PushNotificationPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FcmPushNotificationAdapter implements PushNotificationPort {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendToToken(String token, PushMessage pushMessage) {
        Notification notification = Notification.builder()
                .setTitle(pushMessage.title())
                .setBody(pushMessage.body())
                .build();

        Message.Builder messageBuilder = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putData("type", pushMessage.type().name());

        if (pushMessage.targetId() != null) {
            messageBuilder.putData("targetId", String.valueOf(pushMessage.targetId()));
        }

        if (pushMessage.data() != null && !pushMessage.data().isEmpty()) {
            messageBuilder.putAllData(pushMessage.data());
        }

        try {
            String messageId = firebaseMessaging.send(messageBuilder.build());
            log.info("FCM message sent. messageId={}, token={}", messageId, maskToken(token));
        } catch (FirebaseMessagingException e) {
            log.error("FCM send failed. token={}, error={}", maskToken(token), e.getMessage());
        }
    }

    @Override
    public void sendToTokens(List<String> tokens, PushMessage pushMessage) {
        if (tokens == null || tokens.isEmpty()) {
            return;
        }

        if (tokens.size() == 1) {
            sendToToken(tokens.get(0), pushMessage);
            return;
        }

        Notification notification = Notification.builder()
                .setTitle(pushMessage.title())
                .setBody(pushMessage.body())
                .build();

        MulticastMessage.Builder multicastBuilder = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .putData("type", pushMessage.type().name());

        if (pushMessage.targetId() != null) {
            multicastBuilder.putData("targetId", String.valueOf(pushMessage.targetId()));
        }

        if (pushMessage.data() != null && !pushMessage.data().isEmpty()) {
            multicastBuilder.putAllData(pushMessage.data());
        }

        try {
            BatchResponse response = firebaseMessaging.sendEachForMulticast(multicastBuilder.build());
            log.info("FCM multicast sent. success={}, failure={}",
                    response.getSuccessCount(), response.getFailureCount());

            List<SendResponse> responses = response.getResponses();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    log.warn("FCM send failed for token index {}. error={}",
                            i, responses.get(i).getException().getMessage());
                }
            }
        } catch (FirebaseMessagingException e) {
            log.error("FCM multicast failed. error={}", e.getMessage());
        }
    }

    private String maskToken(String token) {
        if (token == null || token.length() < 10) {
            return "***";
        }
        return token.substring(0, 6) + "..." + token.substring(token.length() - 4);
    }
}
