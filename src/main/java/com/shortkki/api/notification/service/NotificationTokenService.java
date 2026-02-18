package com.shortkki.api.notification.service;

import com.shortkki.api.notification.entity.MemberDevice;
import com.shortkki.api.notification.repository.MemberDeviceRepository;
import com.shortkki.global.entity.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationTokenService {

    private final MemberDeviceRepository memberDeviceRepository;

    public void registerFcmToken(Long memberId, String fcmToken, String deviceId, Platform platform) {
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
                            device -> log.info("기존 토큰 존재 확인. memberId={}", device.getMemberId()),
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
}
