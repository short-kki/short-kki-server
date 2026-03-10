package com.shortkki.api.auth.application.service;

import com.shortkki.api.auth.dto.LoginRequest;
import com.shortkki.api.auth.dto.LoginResponse;
import com.shortkki.api.auth.dto.RefreshTokenResponse;
import com.shortkki.global.entity.Platform;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.entity.OAuthProvider;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.global.auth.jwt.JwtTokenProvider;
import com.shortkki.global.config.OAuth2Properties;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
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
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestClient restClient;
    private final OAuth2Properties oAuth2Properties;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleIdTokenVerifierService googleIdTokenVerifierService;

    @Transactional
    public LoginResponse login(OAuthProvider provider, LoginRequest request) {
        // Google idToken 플로우
        if (provider == OAuthProvider.GOOGLE && request.hasIdToken()) {
            return loginWithGoogleIdToken(request);
        }

        // Naver/Kakao accessToken 플로우
        if ((provider == OAuthProvider.NAVER || provider == OAuthProvider.KAKAO)
                && request.hasAccessToken()) {
            return loginWithAccessToken(provider, request);
        }

        // 기존 Authorization Code 플로우
        return loginWithCode(provider, request);
    }

    private LoginResponse loginWithGoogleIdToken(LoginRequest request) {
        if (request.getPlatform() == null) {
            throw new BusinessException(ErrorCode.PLATFORM_REQUIRED_FOR_GOOGLE);
        }

        log.info("Google idToken login request - platform: {}", request.getPlatform());

        GoogleIdTokenVerifierService.GoogleUserInfo userInfo =
                googleIdTokenVerifierService.verify(request.getIdToken(), request.getPlatform());

        String oauthId = userInfo.oauthId();
        String email = userInfo.email();
        String name = userInfo.name();

        boolean isNewMember = !memberRepository.existsByEmail(email);
        Member member = getOrCreateMember(email, name, oauthId, OAuthProvider.GOOGLE);

        String accessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return LoginResponse.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(member.getEmail())
                .name(member.getName())
                .profileImageUrl(member.getProfileImgUrl())
                .isNewMember(isNewMember)
                .build();
    }

    private LoginResponse loginWithAccessToken(OAuthProvider provider, LoginRequest request) {
        log.info("{} accessToken login request - platform: {}", provider, request.getPlatform());

        String configKey = provider.name().toLowerCase();
        OAuth2Properties.Provider providerConfig = oAuth2Properties.getProvider(configKey);

        // accessToken으로 바로 사용자 정보 조회
        Map<String, Object> userInfo = getUserInfo(request.getAccessToken(), providerConfig,
                provider);

        String oauthId = extractOAuthId(userInfo, provider);
        String email = extractEmail(userInfo, provider);
        String name = extractName(userInfo, provider);

        boolean isNewMember = !memberRepository.existsByEmail(email);
        Member member = getOrCreateMember(email, name, oauthId, provider);

        String accessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return LoginResponse.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(member.getEmail())
                .name(member.getName())
                .profileImageUrl(member.getProfileImgUrl())
                .isNewMember(isNewMember)
                .build();
    }

    private LoginResponse loginWithCode(OAuthProvider provider, LoginRequest request) {
        if (!request.hasCode()) {
            throw new BusinessException(ErrorCode.ID_TOKEN_OR_CODE_REQUIRED);
        }

        log.info(
                "OAuth login request - provider: {}, platform: {}, hasCode: {}, hasCodeVerifier: {}",
                provider,
                request.getPlatform(),
                request.hasCode(),
                request.getCodeVerifier() != null && !request.getCodeVerifier().isBlank());

        String configKey = resolveProviderConfigKey(provider, request.getPlatform());
        OAuth2Properties.Provider providerConfig = oAuth2Properties.getProvider(configKey);

        String oauthAccessToken = getAccessToken(request.getCode(), request.getCodeVerifier(),
                providerConfig, provider);
        Map<String, Object> userInfo = getUserInfo(oauthAccessToken, providerConfig, provider);

        String oauthId = extractOAuthId(userInfo, provider);
        String email = extractEmail(userInfo, provider);
        String name = extractName(userInfo, provider);

        boolean isNewMember = !memberRepository.existsByEmail(email);
        Member member = getOrCreateMember(email, name, oauthId, provider);

        String accessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return LoginResponse.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(member.getEmail())
                .name(member.getName())
                .profileImageUrl(member.getProfileImgUrl())
                .isNewMember(isNewMember)
                .build();
    }

    private String resolveProviderConfigKey(OAuthProvider provider, Platform platform) {
        if (provider == OAuthProvider.GOOGLE) {
            if (platform == null) {
                throw new BusinessException(ErrorCode.PLATFORM_REQUIRED_FOR_GOOGLE);
            }
            return "google-" + platform.name().toLowerCase();
        }
        return provider.name().toLowerCase();
    }

    @SuppressWarnings("unchecked")
    private String getAccessToken(String code, String codeVerifier,
            OAuth2Properties.Provider providerConfig, OAuthProvider provider) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", providerConfig.getClientId());
        params.add("redirect_uri", providerConfig.getRedirectUri());
        params.add("code", code);

        if (provider == OAuthProvider.GOOGLE) {
            if (codeVerifier != null && !codeVerifier.isBlank()) {
                params.add("code_verifier", codeVerifier);
            }
            log.info("Google OAuth request - clientId: {}, redirectUri: {}, hasCodeVerifier: {}",
                    providerConfig.getClientId(),
                    providerConfig.getRedirectUri(),
                    codeVerifier != null && !codeVerifier.isBlank());
        } else {
            params.add("client_secret", providerConfig.getClientSecret());
            log.info("{} OAuth request - clientId: {}, redirectUri: {}, tokenUri: {}",
                    provider,
                    providerConfig.getClientId(),
                    providerConfig.getRedirectUri(),
                    providerConfig.getTokenUri());
        }

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
        if (member.isDeleted()) {
            member.reactivate();
        }

        if (!member.getName().equals(name)) {
            member.updateName(name);
        }

        if (!Objects.equals(member.getOauthId(), oauthId)) {
            member.updateOAuthInfo(oauthId, provider);
        }

        return member;
    }

    private Member createNewMember(String email, String name, String oauthId,
            OAuthProvider provider) {
        Member newMember = Member.create(email, name, oauthId, provider, null);
        return memberRepository.save(newMember);
    }

    @Transactional(readOnly = true)
    public RefreshTokenResponse refreshAccessToken(String accessToken, String refreshToken) {
        validateAccessTokenForRefresh(accessToken);
        validateRefreshToken(refreshToken);

        Long accessTokenMemberId = jwtTokenProvider.getMemberIdFromToken(accessToken);
        Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);

        if (!Objects.equals(accessTokenMemberId, memberId)) {
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        String newAccessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole()
        );
        String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return new RefreshTokenResponse(newAccessToken, newRefreshToken);
    }

    private void validateAccessTokenForRefresh(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (!jwtTokenProvider.isAccessToken(accessToken)) {
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }

        if (jwtTokenProvider.isTokenValid(accessToken)) {
            throw new BadRequestException("아직 유효한 accessToken은 재발급할 수 없습니다.");
        }
    }

    private void validateRefreshToken(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_VALUE);
        }

        jwtTokenProvider.validateToken(refreshToken);

        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }
    }

}
