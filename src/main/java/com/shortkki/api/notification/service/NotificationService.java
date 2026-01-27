package com.shortkki.api.notification.service;

import com.shortkki.api.notification.dto.response.NotificationResponse;
import com.shortkki.api.notification.dto.response.UnreadCountResponse;
import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.repository.NotificationRepository;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public SliceResponse<NotificationResponse> getNotifications(Long memberId, Pageable pageable) {
        return SliceResponse.from(
                notificationRepository.findByReceiverIdOrderByCreatedAtDesc(memberId, pageable)
                        .map(NotificationResponse::from)
        );
    }

    public UnreadCountResponse getUnreadCount(Long memberId) {
        long count = notificationRepository.countByReceiverIdAndIsReadFalse(memberId);
        return UnreadCountResponse.of(count);
    }

    @Transactional
    public void markAsRead(Long memberId, Long notificationId) {
        Notification notification = findNotificationById(notificationId);
        validateNotificationOwner(memberId, notification);
        notification.markAsRead();
    }

    @Transactional
    public void markAllAsRead(Long memberId) {
        notificationRepository.markAllAsReadByReceiverId(memberId);
    }

    private Notification findNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("알림이 존재하지 않습니다."));
    }

    private void validateNotificationOwner(Long memberId, Notification notification) {
        if (!notification.getReceiverId().equals(memberId)) {
            throw new AccessDeniedException("알림에 접근할 수 없습니다.");
        }
    }
}
