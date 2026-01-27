package com.shortkki.api.device.service;

import com.shortkki.api.device.entity.DeviceToken;
import com.shortkki.api.device.entity.DeviceType;
import com.shortkki.api.device.repository.DeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    @Transactional
    public void registerToken(Long memberId, String token, DeviceType deviceType) {
        deviceTokenRepository.findByToken(token)
                .ifPresentOrElse(
                        existingToken -> existingToken.updateMember(memberId),
                        () -> deviceTokenRepository.save(
                                DeviceToken.create(memberId, token, deviceType))
                );
    }

    @Transactional
    public void deleteToken(String token) {
        deviceTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteAllTokensByMember(Long memberId) {
        deviceTokenRepository.deleteByMemberId(memberId);
    }
}
