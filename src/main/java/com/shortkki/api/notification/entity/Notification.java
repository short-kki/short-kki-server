package com.shortkki.api.notification.entity;

import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "notification", indexes = {
        @Index(name = "idx_notification_receiver_id", columnList = "receiverId"),
        @Index(name = "idx_notification_receiver_is_read", columnList = "receiverId, isRead")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long receiverId;

    private Long senderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String content;

    private String relatedUrl;

    private Long targetId;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Builder
    private Notification(Long receiverId, Long senderId, NotificationType notificationType,
                         String content, String relatedUrl, Long targetId) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.notificationType = notificationType;
        this.content = content;
        this.relatedUrl = relatedUrl;
        this.targetId = targetId;
        this.isRead = false;
    }

    public static Notification create(Long receiverId, Long senderId, NotificationType notificationType,
                                       String content, String relatedUrl, Long targetId) {
        return Notification.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .notificationType(notificationType)
                .content(content)
                .relatedUrl(relatedUrl)
                .targetId(targetId)
                .build();
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
