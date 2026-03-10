package com.shortkki.api.auth.application.port;

import java.time.Duration;

public interface RefreshTokenStore {

    void store(Long memberId, String refreshToken, Duration ttl);

    String find(Long memberId);

    void delete(Long memberId);
}
