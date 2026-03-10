package com.shortkki.api.auth.infra.redis;

import com.shortkki.api.auth.application.port.AccessTokenBlacklist;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisAccessTokenBlacklist implements AccessTokenBlacklist {

    private static final String KEY_PREFIX = "bl:at:";
    private static final String BLACKLISTED = "1";

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void add(String accessToken, Duration ttl) {
        if (ttl.isPositive()) {
            stringRedisTemplate.opsForValue().set(key(accessToken), BLACKLISTED, ttl);
        }
    }

    @Override
    public boolean isBlacklisted(String accessToken) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key(accessToken)));
    }

    private String key(String accessToken) {
        return KEY_PREFIX + accessToken;
    }
}
