package com.shortkki.api.notification.service;

import com.shortkki.api.notification.controller.dto.NotificationSliceResponse;
import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.repository.NotificationRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public NotificationSliceResponse getNotifications(Long memberId, Long cursorId, int size) {
        Slice<Notification> slice = notificationRepository.findByReceiverIdWithCursor(
                memberId, cursorId, PageRequest.of(0, size));
        return NotificationSliceResponse.from(slice);
    }

    public long getUnreadCount(Long memberId) {
        return notificationRepository.countByReceiverIdAndIsReadFalse(memberId);
    }

    @Transactional
    public void markAsRead(Long memberId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if (!notification.getReceiverId().equals(memberId)) {
            throw new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }

        notification.markAsRead();
    }

    @Transactional
    public int markAllAsRead(Long memberId) {
        return notificationRepository.markAllAsReadByReceiverId(memberId);
    }
}
