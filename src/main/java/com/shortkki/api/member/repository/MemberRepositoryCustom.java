package com.shortkki.api.member.repository;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.entity.OAuthProvider;
import com.shortkki.api.member.entity.Role;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    List<Member> searchByName(String name);

    List<Member> findWithDynamicCond(String name, String email, OAuthProvider oauthProvider,
            Role role);

    Optional<Member> findByEmailWithProvider(String email, OAuthProvider oauthProvider);

    List<Member> findAllByRole(Role role);

    long countByOAuthProvider(OAuthProvider oauthProvider);
}
