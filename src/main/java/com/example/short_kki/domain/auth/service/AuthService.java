package com.example.short_kki.domain.auth.service;

import com.example.short_kki.domain.auth.dto.LoginRequest;
import com.example.short_kki.domain.auth.dto.LoginResponse;
import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.entity.OAuthProvider;
import com.example.short_kki.domain.member.repository.MemberRepository;
import com.example.short_kki.global.auth.jwt.JwtTokenProvider;
import com.example.short_kki.global.config.OAuth2Properties;
import com.example.short_kki.global.error.exception.BusinessException;
import com.example.short_kki.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestClient restClient;
    private final OAuth2Properties oAuth2Properties;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(String providerName, LoginRequest request) {
        OAuthProvider provider = parseProvider(providerName);
        OAuth2Properties.Provider providerConfig = oAuth2Properties.getProvider(providerName);

        String oauthAccessToken = getAccessToken(request.getCode(), providerConfig);
        Map<String, Object> userInfo = getUserInfo(oauthAccessToken, providerConfig, provider);

        String oauthId = extractOAuthId(userInfo, provider);
        String email = extractEmail(userInfo, provider);
        String name = extractName(userInfo, provider);

        boolean isNewMember = !memberRepository.existsByEmail(email);
        Member member = getOrCreateMember(email, name, oauthId, provider);

        String accessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole()
        );
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        log.info("Login success - provider: {}, email: {}, isNewMember: {}", provider, email,
                isNewMember);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(member.getEmail())
                .name(member.getName())
                .isNewMember(isNewMember)
                .build();
    }

    private OAuthProvider parseProvider(String providerName) {
        try {
            return OAuthProvider.valueOf(providerName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.OAUTH_AUTHENTICATION_FAILED,
                    "지원하지 않는 OAuth 제공자입니다: " + providerName);
        }
    }

    @SuppressWarnings("unchecked")
    private String getAccessToken(String code, OAuth2Properties.Provider providerConfig) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", providerConfig.getClientId());
        params.add("client_secret", providerConfig.getClientSecret());
        params.add("redirect_uri", providerConfig.getRedirectUri());
        params.add("code", code);

        try {
            Map<String, Object> response = restClient.post()
                    .uri(providerConfig.getTokenUri())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(params)
                    .retrieve()
                    .body(Map.class);

            if (response == null || !response.containsKey("access_token")) {
                throw new BusinessException(ErrorCode.OAUTH_AUTHENTICATION_FAILED,
                        "OAuth Access Token 발급에 실패했습니다.");
            }

            return (String) response.get("access_token");
        } catch (RestClientException e) {
            log.error("Failed to get access token: {}", e.getMessage());
            throw new BusinessException(ErrorCode.OAUTH_AUTHENTICATION_FAILED,
                    "OAuth 인증 서버 통신에 실패했습니다.");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getUserInfo(String accessToken,
            OAuth2Properties.Provider providerConfig, OAuthProvider provider) {
        try {
            RestClient.RequestHeadersSpec<?> requestSpec = restClient.get()
                    .uri(providerConfig.getUserInfoUri())
                    .header("Authorization", "Bearer " + accessToken);

            if (provider == OAuthProvider.KAKAO) {
                requestSpec.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            }

            Map<String, Object> response = requestSpec
                    .retrieve()
                    .body(Map.class);

            if (response == null) {
                throw new BusinessException(ErrorCode.OAUTH_AUTHENTICATION_FAILED,
                        "사용자 정보를 가져오는데 실패했습니다.");
            }

            return response;
        } catch (RestClientException e) {
            log.error("Failed to get user info: {}", e.getMessage());
            throw new BusinessException(ErrorCode.OAUTH_AUTHENTICATION_FAILED,
                    "OAuth 사용자 정보 조회에 실패했습니다.");
        }
    }

    @SuppressWarnings("unchecked")
    private String extractOAuthId(Map<String, Object> userInfo, OAuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> (String) userInfo.get("sub");
            case KAKAO -> String.valueOf(userInfo.get("id"));
            case NAVER -> {
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                yield (String) response.get("id");
            }
        };
    }

    @SuppressWarnings("unchecked")
    private String extractEmail(Map<String, Object> userInfo, OAuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> (String) userInfo.get("email");
            case KAKAO -> {
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get(
                        "kakao_account");
                yield (String) kakaoAccount.get("email");
            }
            case NAVER -> {
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                yield (String) response.get("email");
            }
        };
    }

    @SuppressWarnings("unchecked")
    private String extractName(Map<String, Object> userInfo, OAuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> (String) userInfo.get("name");
            case KAKAO -> {
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get(
                        "kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                yield (String) profile.get("nickname");
            }
            case NAVER -> {
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                yield (String) response.get("name");
            }
        };
    }

    private Member getOrCreateMember(String email, String name, String oauthId,
            OAuthProvider provider) {
        return memberRepository.findByEmail(email)
                .map(member -> updateMemberIfNeeded(member, name, oauthId, provider))
                .orElseGet(() -> createNewMember(email, name, oauthId, provider));
    }

    private Member updateMemberIfNeeded(Member member, String name, String oauthId,
            OAuthProvider provider) {
        boolean needsUpdate = false;

        if (!member.getName().equals(name)) {
            member.updateName(name);
            needsUpdate = true;
        }

        if (!member.getOauthId().equals(oauthId)) {
            member.updateOAuthInfo(oauthId, provider);
            needsUpdate = true;
        }

        if (needsUpdate) {
            log.info("Updated existing member: {}", member.getEmail());
        }

        return member;
    }

    private Member createNewMember(String email, String name, String oauthId,
            OAuthProvider provider) {
        Member newMember = Member.create(email, name, oauthId, provider);
        Member savedMember = memberRepository.save(newMember);
        log.info("Created new member: {}", savedMember.getEmail());
        return savedMember;
    }
}
