package com.shortkki.api.device.entity;

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

@Entity
@Table(name = "device_token", indexes = {
        @Index(name = "idx_device_token_member_id", columnList = "memberId")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType deviceType;

    @Builder
    private DeviceToken(Long memberId, String token, DeviceType deviceType) {
        this.memberId = memberId;
        this.token = token;
        this.deviceType = deviceType;
    }

    public static DeviceToken create(Long memberId, String token, DeviceType deviceType) {
        return DeviceToken.builder()
                .memberId(memberId)
                .token(token)
                .deviceType(deviceType)
                .build();
    }

    public void updateMember(Long memberId) {
        this.memberId = memberId;
    }
}
