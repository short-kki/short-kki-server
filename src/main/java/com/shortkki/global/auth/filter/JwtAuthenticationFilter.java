package com.shortkki.global.auth.filter;

import com.shortkki.api.auth.application.port.AccessTokenBlacklist;
import com.shortkki.global.auth.jwt.JwtTokenProvider;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final AccessTokenBlacklist accessTokenBlacklist;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {
            try {
                if (jwtTokenProvider.validateToken(token)) {
                    if (!jwtTokenProvider.isAccessToken(token)) {
                        throw new BusinessException(ErrorCode.INVALID_TOKEN);
                    }
                    try {
                        String jti = jwtTokenProvider.getJti(token);
                        if (jti == null) {
                            throw new BusinessException(ErrorCode.INVALID_TOKEN);
                        }
                        if (accessTokenBlacklist.isBlacklisted(jti)) {
                            throw new BusinessException(ErrorCode.INVALID_TOKEN);
                        }
                    } catch (BusinessException e) {
                        throw e;
                    } catch (Exception e) {
                        log.warn("블랙리스트 조회 실패, 건너뜀: {}", e.getMessage());
                    }
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug(
                            "Set Authentication to SecurityContext for '{}', uri: {}",
                            authentication.getName(), request.getRequestURI()
                    );
                }
            } catch (BusinessException e) {
                log.warn("JWT validation failed: {}", e.getMessage());
                setErrorResponse(response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void setErrorResponse(HttpServletResponse response, BusinessException e)
            throws IOException {
        response.setStatus(e.getErrorCode().getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        BaseResponse<Void> errorResponse = BaseResponse.error(
                e.getErrorCode().getCode(),
                e.getMessage()
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
