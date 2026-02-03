package com.shortkki.api.group.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.InviteLink;
import com.shortkki.api.group.entity.QInviteLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InviteLinkRepositoryImpl implements InviteLinkRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QInviteLink inviteLink = QInviteLink.inviteLink;

    @Override
    public Optional<InviteLink> findValidLinkByGroup(Group group) {
        InviteLink result = queryFactory
                .selectFrom(inviteLink)
                .where(
                        inviteLink.group.eq(group),
                        inviteLink.expiresAt.after(LocalDateTime.now())
                )
                .orderBy(inviteLink.expiresAt.desc())
                .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<InviteLink> findByCode(String code) {
        InviteLink result = queryFactory
                .selectFrom(inviteLink)
                .join(inviteLink.group).fetchJoin()
                .where(inviteLink.code.eq(code))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByCode(String code) {
        Integer result = queryFactory
                .selectOne()
                .from(inviteLink)
                .where(inviteLink.code.eq(code))
                .fetchFirst();
        return result != null;
    }
}
