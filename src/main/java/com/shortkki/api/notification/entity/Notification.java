package com.shortkki.api.notification.entity;

import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notification", indexes = {
        @Index(name = "idx_notification_receiver_id", columnList = "receiverId"),
        @Index(name = "idx_notification_receiver_is_read", columnList = "receiverId, isRead")
})
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String content;

    private Long targetId;

    @Column(columnDefinition = "json")
    private String payload;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Builder
    private Notification(Long receiverId, NotificationType notificationType,
            String content, Long targetId, String payload) {
        this.receiverId = receiverId;
        this.notificationType = notificationType;
        this.content = content;
        this.targetId = targetId;
        this.payload = payload;
        this.isRead = false;
    }

    public static Notification create(Long receiverId, NotificationType notificationType,
            String content, Long targetId, String payload) {
        return Notification.builder()
                .receiverId(receiverId)
                .notificationType(notificationType)
                .content(content)
                .targetId(targetId)
                .payload(payload)
                .build();
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
