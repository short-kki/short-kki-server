package com.shortkki.api.auth.dto;

import com.shortkki.global.entity.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    // Authorization code flow (기존 방식)
    private String code;
    private String codeVerifier;

    // idToken flow (Google 모바일 앱용)
    private String idToken;

    // accessToken flow (Naver/Kakao 모바일 앱용)
    private String accessToken;

    private Platform platform;

    public boolean hasIdToken() {
        return idToken != null && !idToken.isBlank();
    }

    public boolean hasAccessToken() {
        return accessToken != null && !accessToken.isBlank();
    }

    public boolean hasCode() {
        return code != null && !code.isBlank();
    }
}
