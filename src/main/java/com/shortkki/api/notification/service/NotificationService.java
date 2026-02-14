package com.shortkki.api.notification.service;

import com.shortkki.api.notification.application.dto.PushMessage;
import com.shortkki.api.notification.application.port.PushNotificationPort;
import com.shortkki.api.notification.entity.DevicePlatform;
import com.shortkki.api.notification.entity.MemberDevice;
import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.entity.NotificationType;
import com.shortkki.api.notification.repository.MemberDeviceRepository;
import com.shortkki.api.notification.repository.NotificationRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberDeviceRepository memberDeviceRepository;
    private final PushNotificationPort pushNotificationPort;

    public void registerFcmToken(Long memberId, String fcmToken, String deviceId,
            DevicePlatform platform) {
        log.info("FCM 토큰 등록 요청. memberId={}, platform={}, token={}...{}",
                memberId, platform,
                fcmToken.substring(0, Math.min(10, fcmToken.length())),
                fcmToken.substring(Math.max(0, fcmToken.length() - 6)));

        // 같은 토큰이 다른 사용자에게 등록되어 있으면 삭제
        memberDeviceRepository.findByFcmToken(fcmToken)
                .ifPresent(existing -> {
                    if (!existing.getMemberId().equals(memberId)) {
                        log.info("다른 사용자의 토큰 삭제. previousMemberId={}", existing.getMemberId());
                        memberDeviceRepository.delete(existing);
                    }
                });

        // deviceId가 있으면 기존 디바이스 업데이트, 없으면 새로 생성
        if (deviceId != null && !deviceId.isBlank()) {
            memberDeviceRepository.findByMemberIdAndDeviceId(memberId, deviceId)
                    .ifPresentOrElse(
                            device -> {
                                device.updateToken(fcmToken);
                                log.info("기존 디바이스 토큰 업데이트. deviceId={}", deviceId);
                            },
                            () -> {
                                memberDeviceRepository.save(
                                        MemberDevice.create(memberId, fcmToken, deviceId, platform));
                                log.info("새 디바이스 등록. deviceId={}", deviceId);
                            }
                    );
        } else {
            memberDeviceRepository.findByFcmToken(fcmToken)
                    .ifPresentOrElse(
                            device -> {
                                device.updateToken(fcmToken);
                                log.info("기존 토큰 업데이트.");
                            },
                            () -> {
                                memberDeviceRepository.save(
                                        MemberDevice.create(memberId, fcmToken, deviceId, platform));
                                log.info("새 토큰 등록.");
                            }
                    );
        }

        log.info("FCM 토큰 등록 완료. memberId={}", memberId);
    }

    public void deleteFcmToken(Long memberId, String fcmToken) {
        memberDeviceRepository.deleteByMemberIdAndFcmToken(memberId, fcmToken);
    }

    @Transactional(readOnly = true)
    public Slice<Notification> getNotifications(Long memberId, Long cursorId, int size) {
        return notificationRepository.findByReceiverIdWithCursor(memberId, cursorId, PageRequest.of(0, size));
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(Long memberId) {
        return notificationRepository.countByReceiverIdAndIsReadFalse(memberId);
    }

    public void markAsRead(Long memberId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if (!notification.getReceiverId().equals(memberId)) {
            throw new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }

        notification.markAsRead();
    }

    public int markAllAsRead(Long memberId) {
        return notificationRepository.markAllAsReadByReceiverId(memberId);
    }

    public void createAndSendNotification(Long receiverId, Long senderId, NotificationType type,
            String content, String relatedUrl, Long targetId) {
        Notification notification = Notification.create(receiverId, senderId, type, content, relatedUrl, targetId);
        notificationRepository.save(notification);

        sendPushToMember(receiverId, PushMessage.of(type.getDescription(), content, type, targetId));
    }

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
