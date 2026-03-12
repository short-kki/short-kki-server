package com.shortkki.test.security;

import com.shortkki.global.auth.dto.LoginMember;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public final class WithMockMemberSecurityContextFactory
        implements WithSecurityContextFactory<WithMockMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockMember annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        LoginMember principal = LoginMember.builder()
                .id(annotation.id())
                .email(annotation.email())
                .name(annotation.name())
                .role(annotation.role())
                .jti(UUID.randomUUID().toString())
                .exp(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                principal,
                "N/A",
                principal.getAuthorities()
        );

        context.setAuthentication(auth);
        return context;
    }
}
