package com.shortkki.api.notification.service;

import com.shortkki.api.notification.application.dto.PushMessage;
import com.shortkki.api.notification.application.port.PushNotificationPort;
import com.shortkki.api.notification.entity.MemberDevice;
import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.entity.NotificationType;
import com.shortkki.api.notification.repository.MemberDeviceRepository;
import com.shortkki.api.notification.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSendService {

    private final NotificationRepository notificationRepository;
    private final MemberDeviceRepository memberDeviceRepository;
    private final PushNotificationPort pushNotificationPort;

    @Transactional
    public void createNotification(Long receiverId, NotificationType type,
            String content, Long targetId, String payload) {
        Notification notification = Notification.create(receiverId, type, content, targetId, payload);
        notificationRepository.save(notification);
    }

    @Transactional
    public void createNotifications(List<Long> receiverIds, NotificationType type,
            String content, Long targetId, String payload) {
        List<Notification> notifications = receiverIds.stream()
                .map(receiverId -> Notification.create(receiverId, type, content, targetId, payload))
                .toList();
        notificationRepository.saveAll(notifications);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendPushToMember(Long memberId, PushMessage message) {
        List<MemberDevice> devices = memberDeviceRepository.findByMemberIdAndIsActiveTrue(memberId);
        if (devices.isEmpty()) {
            log.debug("No active devices for member: {}", memberId);
            return;
        }

        List<String> tokens = devices.stream()
                .map(MemberDevice::getFcmToken)
                .toList();

        pushNotificationPort.sendToTokens(tokens, message);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendPushToMembers(List<Long> memberIds, PushMessage message) {
        if (memberIds == null || memberIds.isEmpty()) {
            return;
        }

        List<MemberDevice> devices = memberDeviceRepository.findByMemberIdInAndIsActiveTrue(memberIds);
        if (devices.isEmpty()) {
            log.debug("No active devices for members: {}", memberIds);
            return;
        }

        List<String> tokens = devices.stream()
                .map(MemberDevice::getFcmToken)
                .toList();

        pushNotificationPort.sendToTokens(tokens, message);
    }
}
