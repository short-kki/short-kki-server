package com.example.short_kki.domain.member.repository;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.entity.OAuthProvider;
import com.example.short_kki.domain.member.entity.QMember;
import com.example.short_kki.domain.member.entity.Role;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMember member = QMember.member;

    @Override
    public List<Member> searchByName(String name) {
        return queryFactory
                .selectFrom(member)
                .where(containsName(name))
                .orderBy(member.name.asc())
                .fetch();
    }

    @Override
    public List<Member> findWithDynamicCond(String name, String email, OAuthProvider oauthProvider, Role role) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(name)) {
            builder.and(containsName(name));
        }
        if (StringUtils.hasText(email)) {
            builder.and(containsEmail(email));
        }
        if (oauthProvider != null) {
            builder.and(eqOAuthProvider(oauthProvider));
        }
        if (role != null) {
            builder.and(eqRole(role));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .orderBy(member.id.desc())
                .fetch();
    }

    @Override
    public Optional<Member> findByEmailWithProvider(String email, OAuthProvider oauthProvider) {
        Member result = queryFactory
                .selectFrom(member)
                .where(
                        eqEmail(email),
                        eqOAuthProvider(oauthProvider)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<Member> findAllByRole(Role role) {
        return queryFactory
                .selectFrom(member)
                .where(eqRole(role))
                .orderBy(member.id.asc())
                .fetch();
    }

    @Override
    public long countByOAuthProvider(OAuthProvider oauthProvider) {
        Long count = queryFactory
                .select(member.count())
                .from(member)
                .where(eqOAuthProvider(oauthProvider))
                .fetchOne();

        return count != null ? count : 0L;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? member.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression containsEmail(String email) {
        return StringUtils.hasText(email) ? member.email.containsIgnoreCase(email) : null;
    }

    private BooleanExpression eqEmail(String email) {
        return StringUtils.hasText(email) ? member.email.eq(email) : null;
    }

    private BooleanExpression eqOAuthProvider(OAuthProvider oauthProvider) {
        return oauthProvider != null ? member.oauthProvider.eq(oauthProvider) : null;
    }

    private BooleanExpression eqRole(Role role) {
        return role != null ? member.role.eq(role) : null;
    }
}
