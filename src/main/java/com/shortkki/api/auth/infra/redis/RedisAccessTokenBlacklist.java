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
    public void add(String jti, Duration ttl) {
        if (ttl.isPositive()) {
            stringRedisTemplate.opsForValue().set(key(jti), BLACKLISTED, ttl);
        }
    }

    @Override
    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key(jti)));
    }

    private String key(String jti) {
        return KEY_PREFIX + jti;
    }
}
