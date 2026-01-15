package com.example.short_kki.test.security;

import com.example.short_kki.domain.member.entity.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WithSecurityContext(factory = WithMockMemberSecurityContextFactory.class)
public @interface WithMockMember {

    long id() default 1L;

    String email() default "test@example.com";

    String name() default "test-user";

    Role role() default Role.USER;
}
