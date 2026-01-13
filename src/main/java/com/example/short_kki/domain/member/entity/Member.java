package com.example.short_kki.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", nullable = false)
    private OAuthProvider oauthProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    private Member(String email, String name, String oauthId, OAuthProvider oauthProvider, Role role) {
        this.email = email;
        this.name = name;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.role = role;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateOAuthInfo(String oauthId, OAuthProvider oauthProvider) {
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
    }

    public static Member create(String email, String name, String oauthId, OAuthProvider oauthProvider) {
        return Member.builder()
                .email(email)
                .name(name)
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .role(Role.USER)
                .build();
    }
}
