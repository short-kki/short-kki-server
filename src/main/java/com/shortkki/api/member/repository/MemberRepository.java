package com.shortkki.api.member.repository;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.entity.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByOauthIdAndOauthProvider(String oauthId, OAuthProvider oauthProvider);

    boolean existsByEmail(String email);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.profileImgFile WHERE m.id = :id")
    Optional<Member> findByIdWithProfileImg(@Param("id") Long id);
}
