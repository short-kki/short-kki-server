package com.example.short_kki.test.security;

import com.example.short_kki.global.auth.dto.LoginMember;
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
