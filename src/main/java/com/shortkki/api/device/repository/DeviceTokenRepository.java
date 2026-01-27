package com.shortkki.api.device.repository;

import com.shortkki.api.device.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    List<DeviceToken> findByMemberId(Long memberId);

    Optional<DeviceToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteByMemberId(Long memberId);
}
