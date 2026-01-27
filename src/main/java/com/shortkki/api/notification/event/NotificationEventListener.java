package com.shortkki.api.notification.event;

import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.repository.NotificationRepository;
import com.shortkki.api.notification.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotificationEvent(NotificationEvent event) {
        saveNotification(event);
        sendPushNotification(event);
    }

    private void saveNotification(NotificationEvent event) {
        try {
            Notification notification = Notification.create(
                    event.receiverId(),
                    event.senderId(),
                    event.notificationType(),
                    event.content(),
                    event.relatedUrl(),
                    event.targetId()
            );
            notificationRepository.save(notification);
            log.debug("Notification saved: type={}, receiverId={}",
                    event.notificationType(), event.receiverId());
        } catch (Exception e) {
            log.error("Failed to save notification: type={}, receiverId={}",
                    event.notificationType(), event.receiverId(), e);
        }
    }

    private void sendPushNotification(NotificationEvent event) {
        try {
            fcmService.sendPush(
                    event.receiverId(),
                    event.notificationType(),
                    event.content(),
                    event.relatedUrl()
            );
        } catch (Exception e) {
            log.error("Failed to send push notification: type={}, receiverId={}",
                    event.notificationType(), event.receiverId(), e);
        }
    }
}
