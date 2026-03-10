package com.shortkki.api.auth.application.port;

import java.time.Duration;

public interface AccessTokenBlacklist {

    void add(String accessToken, Duration ttl);

    boolean isBlacklisted(String accessToken);
}
