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
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleIdTokenVerifierService {

    private final OAuth2Properties oAuth2Properties;

    private GoogleIdTokenVerifier verifier;
    private Set<String> allowedClientIds;

    @PostConstruct
    public void init() {
        // audience 검증 없이 서명만 검증하는 verifier 생성
        // audience는 수동으로 검증
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .build();

        // 허용된 클라이언트 ID 목록 수집
        this.allowedClientIds = getGoogleClientIds();
        log.info("GoogleIdTokenVerifier initialized. Allowed client IDs: {}", allowedClientIds);
    }

    private Set<String> getGoogleClientIds() {
        Set<String> clientIds = new HashSet<>();

        String[] providerKeys = {"google-ios", "google-android", "google-web"};

        for (String key : providerKeys) {
            try {
                String clientId = oAuth2Properties.getProvider(key).getClientId();
                if (clientId != null && !clientId.isBlank() && !clientId.startsWith("${")) {
                    clientIds.add(clientId);
                    log.info("Added {} client ID: {}", key, clientId);
                }
            } catch (Exception e) {
                log.debug("{} not configured", key);
            }
        }

        return clientIds;
    }

    public GoogleUserInfo verify(String idTokenString, Platform platform) {
        log.info("Verifying Google idToken for platform: {}", platform);

        // 먼저 토큰 payload를 파싱하여 audience 확인
        String tokenAudience = extractAudienceFromToken(idTokenString);
        log.info("Token audience: {}", tokenAudience);

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                log.error("Google idToken signature verification failed");
                log.error("Token (first 100 chars): {}...",
                        idTokenString.substring(0, Math.min(100, idTokenString.length())));
                throw new BusinessException(ErrorCode.INVALID_ID_TOKEN,
                        "토큰 서명 검증에 실패했습니다.");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String aud = (String) payload.getAudience();

            // audience 검증 (설정된 클라이언트 ID가 있는 경우에만)
            if (!allowedClientIds.isEmpty() && !allowedClientIds.contains(aud)) {
                log.error("Token audience '{}' not in allowed list: {}", aud, allowedClientIds);
                log.error("Add this client ID to oauth2.provider.google-ios/android/web.client-id in application.yaml");
                throw new BusinessException(ErrorCode.INVALID_ID_TOKEN,
                        "허용되지 않은 클라이언트입니다. audience: " + aud);
            }

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String sub = payload.getSubject();

            log.info("Google idToken verified successfully - email: {}, sub: {}, aud: {}",
                    email, sub, aud);

            return new GoogleUserInfo(sub, email, name);

        } catch (BusinessException e) {
            throw e;
        } catch (GeneralSecurityException | IOException e) {
            log.error("Google idToken verification error: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN,
                    "idToken 검증 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private String extractAudienceFromToken(String idTokenString) {
        try {
            String[] parts = idTokenString.split("\\.");
            if (parts.length >= 2) {
                String payloadJson = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
                log.info("Token payload: {}", payloadJson);

                // 간단히 aud 추출
                int audIndex = payloadJson.indexOf("\"aud\"");
                if (audIndex != -1) {
                    int colonIndex = payloadJson.indexOf(":", audIndex);
                    int quoteStart = payloadJson.indexOf("\"", colonIndex);
                    int quoteEnd = payloadJson.indexOf("\"", quoteStart + 1);
                    if (quoteStart != -1 && quoteEnd != -1) {
                        return payloadJson.substring(quoteStart + 1, quoteEnd);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to extract audience from token: {}", e.getMessage());
        }
        return "unknown";
    }

    public record GoogleUserInfo(String oauthId, String email, String name) {
    }
}
