package com.shortkki.api.auth.application.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.shortkki.global.config.OAuth2Properties;
import com.shortkki.global.entity.Platform;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleIdTokenVerifierService {

    private final OAuth2Properties oAuth2Properties;

    private GoogleIdTokenVerifier verifier;
    private String webClientId;

    @PostConstruct
    public void init() {
        // audience 검증 없이 서명만 검증하는 verifier 생성
        // audience는 수동으로 Web Client ID와 비교 검증
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .build();

        this.webClientId = resolveWebClientId();

        log.info("GoogleIdTokenVerifier initialized with Web Client ID");
    }

    private String resolveWebClientId() {
        try {
            String clientId = oAuth2Properties.getProvider("google-web").getClientId();
            if (clientId != null && !clientId.isBlank() && !clientId.startsWith("${")) {
                return clientId;
            }
        } catch (Exception e) {
            // fall through
        }
        throw new IllegalStateException(
                "Google Web Client ID not configured. Set oauth2.provider.google-web.client-id");
    }

    public GoogleUserInfo verify(String idTokenString, Platform platform) {
        log.debug("Verifying Google idToken for platform: {}", platform);

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                log.error("Google idToken signature verification failed");
                throw new BusinessException(ErrorCode.INVALID_ID_TOKEN,
                        "토큰 서명 검증에 실패했습니다.");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String aud = (String) payload.getAudience();

            // audience 검증 (iOS/Android SDK의 idToken도 aud는 항상 Web Client ID)
            if (!webClientId.equals(aud)) {
                log.error("Token audience mismatch. expected={}, actual={}", webClientId, aud);
                throw new BusinessException(ErrorCode.INVALID_ID_TOKEN,
                        "허용되지 않은 클라이언트입니다.");
            }

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String sub = payload.getSubject();

            return new GoogleUserInfo(sub, email, name);

        } catch (BusinessException e) {
            throw e;
        } catch (GeneralSecurityException | IOException e) {
            log.error("Google idToken verification error: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN,
                    "idToken 검증 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public record GoogleUserInfo(String oauthId, String email, String name) {
    }
}
