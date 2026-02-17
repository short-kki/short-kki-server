package com.shortkki.api.notification.repository;

import com.shortkki.api.notification.entity.MemberDevice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDeviceRepository extends JpaRepository<MemberDevice, Long> {

    List<MemberDevice> findByMemberIdAndIsActiveTrue(Long memberId);

    List<MemberDevice> findByMemberIdInAndIsActiveTrue(List<Long> memberIds);

    Optional<MemberDevice> findByMemberIdAndDeviceId(Long memberId, String deviceId);

    Optional<MemberDevice> findByFcmToken(String fcmToken);

    void deleteByMemberIdAndFcmToken(Long memberId, String fcmToken);
}
