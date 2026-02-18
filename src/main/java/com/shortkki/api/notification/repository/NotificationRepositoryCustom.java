package com.shortkki.api.notification.repository;

import com.shortkki.api.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NotificationRepositoryCustom {

    Slice<Notification> findByReceiverIdWithCursor(Long receiverId, Long cursorId, Pageable pageable);
}
