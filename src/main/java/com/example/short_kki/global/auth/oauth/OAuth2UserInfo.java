package com.example.short_kki.global.auth.oauth;

import com.example.short_kki.domain.member.entity.OAuthProvider;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuth2UserInfo {

    private final String oauthId;
    private final String email;
    private final String name;
    private final OAuthProvider provider;

    @SuppressWarnings("unchecked")
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            case "naver" -> ofNaver(attributes);
            default -> throw new IllegalArgumentException("Unsupported OAuth provider: " + registrationId);
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .oauthId((String) attributes.get("sub"))
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .provider(OAuthProvider.GOOGLE)
                .build();
    }

    @SuppressWarnings("unchecked")
    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2UserInfo.builder()
                .oauthId(String.valueOf(attributes.get("id")))
                .email((String) kakaoAccount.get("email"))
                .name((String) profile.get("nickname"))
                .provider(OAuthProvider.KAKAO)
                .build();
    }

    @SuppressWarnings("unchecked")
    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2UserInfo.builder()
                .oauthId((String) response.get("id"))
                .email((String) response.get("email"))
                .name((String) response.get("name"))
                .provider(OAuthProvider.NAVER)
                .build();
    }
}
