package com.shortkki.api.notification.event.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortkki.api.notification.application.dto.PushMessage;
import com.shortkki.api.notification.event.NotificationEvent;
import com.shortkki.api.notification.service.NotificationSendService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventHandler {

    private final NotificationSendService notificationSendService;
    private final ObjectMapper objectMapper;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(NotificationEvent event) {
        log.info("Handling notification event. type={}, receiverIds={}", event.type(), event.receiverIds());

        try {
            // 1. 알림 일괄 저장
            notificationSendService.createNotifications(
                    event.receiverIds(),
                    event.senderId(),
                    event.type(),
                    event.content(),
                    event.relatedUrl(),
                    event.targetId(),
                    event.payload()
            );

            // 2. 푸시 일괄 전송
            Map<String, String> data = parsePayload(event.payload());
            PushMessage message = PushMessage.of(
                    event.type().getDescription(),
                    event.content(),
                    event.type(),
                    event.targetId(),
                    data
            );
            notificationSendService.sendPushToMembers(event.receiverIds(), message);

        } catch (Exception e) {
            log.error("Failed to handle notification event. type={}, receiverIds={}, error={}",
                    event.type(), event.receiverIds(), e.getMessage());
        }
    }

    private Map<String, String> parsePayload(String payload) {
        if (payload == null || payload.isBlank()) {
            return Map.of();
        }
        try {
            return objectMapper.readValue(payload, new TypeReference<>() {});
        } catch (Exception e) {
            log.warn("Failed to parse payload. payload={}", payload);
            return Map.of();
        }
    }
}
