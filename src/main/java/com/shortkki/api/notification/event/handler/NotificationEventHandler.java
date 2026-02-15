package com.shortkki.api.notification.event.handler;

import com.shortkki.api.notification.application.dto.PushMessage;
import com.shortkki.api.notification.event.NotificationEvent;
import com.shortkki.api.notification.service.NotificationService;
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

    private final NotificationService notificationService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(NotificationEvent event) {
        log.info("Handling notification event. type={}, receiverIds={}", event.type(), event.receiverIds());

        for (Long receiverId : event.receiverIds()) {
            try {
                notificationService.createNotification(
                        receiverId,
                        event.senderId(),
                        event.type(),
                        event.content(),
                        event.relatedUrl(),
                        event.targetId()
                );
                PushMessage message = PushMessage.of(
                        event.type().getDescription(),
                        event.content(),
                        event.type(),
                        event.targetId()
                );
                notificationService.sendPushToMember(receiverId, message);
            } catch (Exception e) {
                log.error("Failed to send notification to receiver: {}. error={}", receiverId, e.getMessage());
            }
        }
    }
}
