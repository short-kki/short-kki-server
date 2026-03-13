package com.shortkki.api.auth.application.port;

import java.time.Duration;

public interface AccessTokenBlacklist {

    void add(String jti, Duration ttl);

    boolean isBlacklisted(String jti);
}
