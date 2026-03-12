package com.shortkki.global.auth.jwt;

import com.shortkki.api.member.entity.Role;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long memberId, String email, Role role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(String.valueOf(memberId))
                .claim("email", email)
                .claim("role", role.name())
                .claim("type", "access")
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    /** 테스트 전용 — 이미 만료된 AT 생성 */
    public String createExpiredAccessToken(Long memberId, String email, Role role) {
        Date now = new Date();
        Date expired = new Date(now.getTime() - 1000); // 1초 전 만료

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(String.valueOf(memberId))
                .claim("email", email)
                .claim("role", role.name())
                .claim("type", "access")
                .issuedAt(new Date(now.getTime() - 2000))
                .expiration(expired)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long memberId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidity);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(String.valueOf(memberId))
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Long memberId = Long.parseLong(claims.getSubject());
        String email = claims.get("email", String.class);
        Role role = Role.valueOf(claims.get("role", String.class));

        LoginMember loginMember = LoginMember.builder()
                .id(memberId)
                .email(email)
                .role(role)
                .jti(claims.getId())
                .exp(claims.getExpiration().toInstant())
                .build();

        return new UsernamePasswordAuthenticationToken(loginMember, "",
                loginMember.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
            throw new BusinessException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Long getMemberIdFromToken(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    public boolean isRefreshToken(String token) {
        Claims claims = parseClaims(token);
        String type = claims.get("type", String.class);
        return "refresh".equals(type);
    }

    public boolean isAccessToken(String token) {
        Claims claims = parseClaims(token);
        String type = claims.get("type", String.class);
        return "access".equals(type);
    }

    public String getJti(String token) {
        Claims claims = parseClaims(token);
        return claims.getId();
    }

    public Duration getRemainingValidity(String token) {
        Claims claims = parseClaims(token);
        long remainingMs = claims.getExpiration().getTime() - System.currentTimeMillis();
        return remainingMs > 0 ? Duration.ofMillis(remainingMs) : Duration.ZERO;
    }
}
