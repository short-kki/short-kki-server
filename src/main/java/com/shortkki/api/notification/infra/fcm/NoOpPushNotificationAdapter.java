package com.shortkki.api.notification.infra.fcm;

import com.shortkki.api.notification.application.dto.PushMessage;
import com.shortkki.api.notification.application.port.PushNotificationPort;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoOpPushNotificationAdapter implements PushNotificationPort {

    @Override
    public void sendToToken(String token, PushMessage message) {
        log.info("[NoOp] FCM disabled. Would send to token: {}, title: {}, body: {}",
                maskToken(token), message.title(), message.body());
    }

    @Override
    public void sendToTokens(List<String> tokens, PushMessage message) {
        log.info("[NoOp] FCM disabled. Would send to {} tokens, title: {}, body: {}",
                tokens != null ? tokens.size() : 0, message.title(), message.body());
    }

    private String maskToken(String token) {
        if (token == null || token.length() < 10) {
            return "***";
        }
        return token.substring(0, 6) + "..." + token.substring(token.length() - 4);
    }
}
