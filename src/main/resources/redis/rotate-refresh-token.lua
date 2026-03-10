-- 원자적 Refresh Token 로테이션 (compare-and-swap)
--
-- KEYS[1] = rt:member:{memberId}
-- ARGV[1] = expectedToken (현재 저장된 RT)
-- ARGV[2] = newToken (교체할 새 RT)
-- ARGV[3] = ttl (초 단위)
--
-- 반환: 1 = 교체 성공, 0 = 불일치(탈취 감지), -1 = 키 없음

local stored = redis.call('GET', KEYS[1])

if stored == false then
    return -1
end

if stored == ARGV[1] then
    redis.call('SET', KEYS[1], ARGV[2], 'EX', ARGV[3])
    return 1
end

redis.call('DEL', KEYS[1])
return 0
