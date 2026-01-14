package com.example.short_kki.domain.group.repository;

import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.entity.MemberGroup;
import com.example.short_kki.domain.group.entity.QMemberGroup;
import com.example.short_kki.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberGroupRepositoryImpl implements MemberGroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMemberGroup memberGroup = QMemberGroup.memberGroup;

    @Override
    public Optional<MemberGroup> findByMemberAndGroup(Member member, Group group) {
        MemberGroup result = queryFactory
                .selectFrom(memberGroup)
                .where(
                        memberGroup.member.eq(member),
                        memberGroup.group.eq(group)
                )
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByMemberAndGroup(Member member, Group group) {
        Integer result = queryFactory
                .selectOne()
                .from(memberGroup)
                .where(
                        memberGroup.member.eq(member),
                        memberGroup.group.eq(group)
                )
                .fetchFirst();
        return result != null;
    }

    @Override
    public List<MemberGroup> findAllByMemberWithGroup(Member member) {
        return queryFactory
                .selectFrom(memberGroup)
                .join(memberGroup.group).fetchJoin()
                .where(memberGroup.member.eq(member))
                .fetch();
    }

    @Override
    public List<MemberGroup> findAllByGroupWithMember(Group group) {
        return queryFactory
                .selectFrom(memberGroup)
                .join(memberGroup.member).fetchJoin()
                .where(memberGroup.group.eq(group))
                .fetch();
    }

    @Override
    public long countByGroup(Group group) {
        Long count = queryFactory
                .select(memberGroup.count())
                .from(memberGroup)
                .where(memberGroup.group.eq(group))
                .fetchOne();
        return count != null ? count : 0L;
    }
}
