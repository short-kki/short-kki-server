package com.shortkki.api.auth.application.port;

import java.time.Duration;

public interface RefreshTokenStore {

    void store(Long memberId, String refreshToken, Duration ttl);

    /**
     * 원자적 토큰 로테이션 (compare-and-swap).
     * 저장된 RT가 expectedToken과 일치하면 newToken으로 교체한다.
     *
     * @return 일치하여 교체 성공하면 true, 불일치(탈취 감지)면 false
     * @throws com.shortkki.global.error.exception.BusinessException 저장된 RT가 없으면 REFRESH_TOKEN_NOT_FOUND
     */
    boolean rotate(Long memberId, String expectedToken, String newToken, Duration ttl);

    String find(Long memberId);

    void delete(Long memberId);
}
