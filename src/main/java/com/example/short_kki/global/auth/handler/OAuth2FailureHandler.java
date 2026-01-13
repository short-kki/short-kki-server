package com.example.short_kki.global.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    log.error("OAuth2 authentication failed: {}", exception.getMessage());

    String errorMessage = URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8);

    String targetUrl = UriComponentsBuilder.fromUriString("/login")
        .queryParam("error", "true")
        .queryParam("message", errorMessage)
        .build()
        .toUriString();

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}
