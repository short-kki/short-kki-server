package com.shortkki.global.auth.dto;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.entity.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class LoginMember implements UserDetails {

    private final Long id;
    private final String email;
    private final String name;
    private final Role role;
    private final String jti;

    @Builder
    private LoginMember(Long id, String email, String name, Role role, String jti) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.jti = jti;
    }

    public static LoginMember from(Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
