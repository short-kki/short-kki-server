package com.example.short_kki.global.auth.handler;

import com.example.short_kki.global.auth.dto.LoginMember;
import com.example.short_kki.global.auth.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        LoginMember loginMember = (LoginMember) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.createAccessToken(
                loginMember.getId(),
                loginMember.getEmail(),
                loginMember.getRole()
        );

        String refreshToken = jwtTokenProvider.createRefreshToken(loginMember.getId());

        log.info("OAuth2 login success - memberId: {}, email: {}", loginMember.getId(), loginMember.getEmail());

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
