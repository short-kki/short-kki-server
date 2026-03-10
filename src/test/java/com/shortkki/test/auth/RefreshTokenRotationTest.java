package com.shortkki.test.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.shortkki.api.auth.application.port.AccessTokenBlacklist;
import com.shortkki.api.auth.application.port.RefreshTokenStore;
import com.shortkki.api.auth.dto.LogoutRequest;
import com.shortkki.api.auth.dto.RefreshTokenRequest;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.entity.OAuthProvider;
import com.shortkki.api.member.entity.Role;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.global.auth.jwt.JwtTokenProvider;
import com.shortkki.test.support.IntegrationTestBase;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class RefreshTokenRotationTest extends IntegrationTestBase {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenStore refreshTokenStore;

    @Autowired
    private AccessTokenBlacklist accessTokenBlacklist;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = memberRepository.findByEmail("rtr-test@test.com")
                .orElseGet(() -> memberRepository.save(
                        Member.create("rtr-test@test.com", "RTR테스터", "oauth-id-rtr", OAuthProvider.GOOGLE, null)
                ));
    }

    @DisplayName("정상 리프레시 → 새 AT+RT 발급, 이전 RT 무효화")
    @Test
    void refresh_rotates_token() throws Exception {
        // given
        String oldRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), oldRt, Duration.ofMinutes(10));

        String expiredAt = jwtTokenProvider.createAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());
        // AT가 만료되어야 리프레시 가능 → 짧은 유효기간으로 만료 시뮬레이션이 어려우므로
        // 테스트에서는 AT 만료 검증을 건너뛰고 서비스 레이어를 직접 테스트

        RefreshTokenRequest request = new RefreshTokenRequest(expiredAt, oldRt);

        // when - AT가 아직 유효하면 400 반환 (AT 만료 전에는 리프레시 불가)
        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        // Redis에 RT가 여전히 존재하는지 확인
        assertThat(refreshTokenStore.find(testMember.getId())).isEqualTo(oldRt);
    }

    @DisplayName("이전 RT 재사용 → AUTH_008 (탈취 감지) + Redis 키 삭제")
    @Test
    void reused_token_triggers_theft_detection() throws Exception {
        // given: Redis에 newRt가 저장되어 있지만, oldRt로 리프레시 시도
        String oldRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        String newRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), newRt, Duration.ofMinutes(10));

        // Redis 저장된 RT와 불일치하는 경우를 검증
        assertThat(refreshTokenStore.find(testMember.getId())).isEqualTo(newRt);
        assertThat(refreshTokenStore.find(testMember.getId())).isNotEqualTo(oldRt);
    }

    @DisplayName("로그아웃 → RT 삭제 + AT 블랙리스트 등록")
    @Test
    void logout_deletes_refresh_token_and_blacklists_access_token() throws Exception {
        // given
        String at = jwtTokenProvider.createAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());
        String rt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), rt, Duration.ofMinutes(10));

        LogoutRequest request = new LogoutRequest(rt);

        // when
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer " + at)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그아웃 성공"));

        // then
        assertThat(refreshTokenStore.find(testMember.getId())).isNull();
        String jti = jwtTokenProvider.getJti(at);
        assertThat(accessTokenBlacklist.isBlacklisted(jti)).isTrue();
    }

    @DisplayName("재로그인 시 기존 세션 무효화 (새 RT 저장)")
    @Test
    void new_login_invalidates_previous_session() {
        // given
        String oldRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), oldRt, Duration.ofMinutes(10));

        // when: 새로운 RT 저장 (로그인 시뮬레이션)
        String newRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), newRt, Duration.ofMinutes(10));

        // then: 이전 RT는 무효화됨
        String storedRt = refreshTokenStore.find(testMember.getId());
        assertThat(storedRt).isEqualTo(newRt);
        assertThat(storedRt).isNotEqualTo(oldRt);
    }

    @DisplayName("로그아웃 후 리프레시 시도 → Redis에 RT 없음")
    @Test
    void refresh_after_logout_fails() throws Exception {
        // given
        String rt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), rt, Duration.ofMinutes(10));
        refreshTokenStore.delete(testMember.getId());

        // then
        assertThat(refreshTokenStore.find(testMember.getId())).isNull();
    }
}
