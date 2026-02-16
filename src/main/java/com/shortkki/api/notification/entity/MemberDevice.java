package com.shortkki.api.notification.entity;

import com.shortkki.global.entity.BaseEntity;
import com.shortkki.global.entity.Platform;
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
@Table(name = "member_device", indexes = {
        @Index(name = "idx_member_device_member_id", columnList = "memberId"),
        @Index(name = "idx_member_device_token", columnList = "fcmToken")
})
public class MemberDevice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 512)
    private String fcmToken;

    @Column(length = 100)
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Platform platform;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Builder
    private MemberDevice(Long memberId, String fcmToken, String deviceId, Platform platform) {
        this.memberId = memberId;
        this.fcmToken = fcmToken;
        this.deviceId = deviceId;
        this.platform = platform;
        this.isActive = true;
    }

    public static MemberDevice create(Long memberId, String fcmToken, String deviceId, Platform platform) {
        return MemberDevice.builder()
                .memberId(memberId)
                .fcmToken(fcmToken)
                .deviceId(deviceId)
                .platform(platform)
                .build();
    }

    public void updateToken(String fcmToken) {
        this.fcmToken = fcmToken;
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }
}
