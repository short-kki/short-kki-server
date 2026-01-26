package com.shortkki.api.member.repository;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.entity.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByOauthIdAndOauthProvider(String oauthId, OAuthProvider oauthProvider);

    boolean existsByEmail(String email);
}
