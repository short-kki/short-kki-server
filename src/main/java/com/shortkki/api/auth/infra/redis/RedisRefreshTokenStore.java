package com.shortkki.api.auth.infra.redis;

import com.shortkki.api.auth.application.port.RefreshTokenStore;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BusinessException;
import java.time.Duration;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRefreshTokenStore implements RefreshTokenStore {

    private static final String KEY_PREFIX = "rt:member:";

    private static final DefaultRedisScript<Long> ROTATE_REDIS_SCRIPT;

    static {
        ROTATE_REDIS_SCRIPT = new DefaultRedisScript<>();
        ROTATE_REDIS_SCRIPT.setLocation(new ClassPathResource("redis/rotate-refresh-token.lua"));
        ROTATE_REDIS_SCRIPT.setResultType(Long.class);
    }

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void store(Long memberId, String refreshToken, Duration ttl) {
        stringRedisTemplate.opsForValue().set(key(memberId), refreshToken, ttl);
    }

    @Override
    public boolean rotate(Long memberId, String expectedToken, String newToken, Duration ttl) {
        Long result = stringRedisTemplate.execute(
                ROTATE_REDIS_SCRIPT,
                Collections.singletonList(key(memberId)),
                expectedToken,
                newToken,
                String.valueOf(ttl.getSeconds())
        );

        if (result == null || result == -1L) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        return result == 1L;
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
