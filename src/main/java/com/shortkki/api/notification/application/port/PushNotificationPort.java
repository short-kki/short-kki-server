package com.shortkki.api.notification.application.port;

import com.shortkki.api.notification.application.dto.PushMessage;
import java.util.List;

public interface PushNotificationPort {

    void sendToToken(String token, PushMessage message);

    void sendToTokens(List<String> tokens, PushMessage message);
}
