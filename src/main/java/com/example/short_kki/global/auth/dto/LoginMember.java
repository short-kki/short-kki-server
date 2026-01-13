package com.example.short_kki.global.auth.dto;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class LoginMember implements UserDetails, OAuth2User {

    private final Long id;
    private final String email;
    private final String name;
    private final Role role;
    private Map<String, Object> attributes;

    @Builder
    private LoginMember(Long id, String email, String name, Role role, Map<String, Object> attributes) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.attributes = attributes;
    }

    public static LoginMember from(Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }

    public static LoginMember of(Member member, Map<String, Object> attributes) {
        return LoginMember.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .attributes(attributes)
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
