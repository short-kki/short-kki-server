package com.shortkki.test.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.shortkki.api.auth.application.port.AccessTokenBlacklist;
import com.shortkki.api.auth.application.port.RefreshTokenStore;
import com.shortkki.api.auth.dto.RefreshTokenRequest;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.entity.OAuthProvider;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.global.auth.jwt.JwtTokenProvider;
import com.shortkki.test.support.IntegrationTestBase;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
        // 테스트 간 격리
        refreshTokenStore.delete(testMember.getId());
    }

    @DisplayName("이전 RT 재사용 → AUTH_008 (탈취 감지) + 전체 세션 강제 종료")
    @Test
    void reused_token_triggers_theft_detection() throws Exception {
        // given: oldRt는 이미 교체된 이전 RT, newRt가 현재 유효한 RT
        String oldRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        String newRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), newRt, Duration.ofMinutes(10));

        String expiredAt = jwtTokenProvider.createExpiredAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());

        // when: 이전 RT로 refresh 시도
        RefreshTokenRequest request = new RefreshTokenRequest(expiredAt, oldRt);
        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTH_008"));

        // then: 전체 세션 삭제 — newRt도 사용 불가
        assertThat(refreshTokenStore.find(testMember.getId())).isNull();
    }

    @DisplayName("로그아웃 후 AT 사용 → 블랙리스트 차단")
    @Test
    void blacklisted_at_is_rejected_after_logout() throws Exception {
        // given
        String at = jwtTokenProvider.createAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());
        String rt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), rt, Duration.ofMinutes(10));

        // when: 로그아웃
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer " + at))
                .andExpect(status().isOk());

        // then: 로그아웃된 AT로 API 호출 → 차단
        mockMvc.perform(get("/api/v1/members/profile")
                        .header("Authorization", "Bearer " + at))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("로그아웃 후 refresh 시도 → AUTH_009 (RT 미존재)")
    @Test
    void refresh_after_logout_returns_auth_009() throws Exception {
        // given
        String at = jwtTokenProvider.createAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());
        String rt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), rt, Duration.ofMinutes(10));

        // 로그아웃
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer " + at))
                .andExpect(status().isOk());

        // when: 로그아웃 후 refresh 시도
        String expiredAt = jwtTokenProvider.createExpiredAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());
        RefreshTokenRequest request = new RefreshTokenRequest(expiredAt, rt);
        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTH_009"));
    }

    @DisplayName("재로그인 시 기존 세션 무효화 → 이전 RT로 refresh 실패")
    @Test
    void new_login_invalidates_previous_session() throws Exception {
        // given: 첫 번째 로그인
        String oldRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), oldRt, Duration.ofMinutes(10));

        // when: 두 번째 로그인 (기존 RT 덮어씀)
        String newRt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), newRt, Duration.ofMinutes(10));

        // then: 이전 RT로 refresh 시도 → 탈취 감지
        String expiredAt = jwtTokenProvider.createExpiredAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());
        RefreshTokenRequest request = new RefreshTokenRequest(expiredAt, oldRt);
        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTH_008"));
    }

    @DisplayName("로그아웃 → RT 삭제 + AT 블랙리스트 등록")
    @Test
    void logout_deletes_rt_and_blacklists_at() throws Exception {
        // given
        String at = jwtTokenProvider.createAccessToken(
                testMember.getId(), testMember.getEmail(), testMember.getRole());
        String rt = jwtTokenProvider.createRefreshToken(testMember.getId());
        refreshTokenStore.store(testMember.getId(), rt, Duration.ofMinutes(10));

        // when
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer " + at))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그아웃 성공"));

        // then
        assertThat(refreshTokenStore.find(testMember.getId())).isNull();
        assertThat(accessTokenBlacklist.isBlacklisted(jwtTokenProvider.getJti(at))).isTrue();
    }
}
