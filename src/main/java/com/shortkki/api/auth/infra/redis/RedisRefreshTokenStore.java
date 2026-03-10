package com.shortkki.api.auth.infra.redis;

import com.shortkki.api.auth.application.port.RefreshTokenStore;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRefreshTokenStore implements RefreshTokenStore {

    private static final String KEY_PREFIX = "rt:member:";

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void store(Long memberId, String refreshToken, Duration ttl) {
        stringRedisTemplate.opsForValue().set(key(memberId), refreshToken, ttl);
    }

    @Override
    public String find(Long memberId) {
        return stringRedisTemplate.opsForValue().get(key(memberId));
    }

    @Override
    public void delete(Long memberId) {
        stringRedisTemplate.delete(key(memberId));
    }

    private String key(Long memberId) {
        return KEY_PREFIX + memberId;
    }
}
