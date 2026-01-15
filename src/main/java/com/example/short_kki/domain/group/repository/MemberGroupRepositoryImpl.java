package com.example.short_kki.domain.group.repository;

import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.entity.MemberGroup;
import com.example.short_kki.domain.group.entity.QMemberGroup;
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
    public Optional<MemberGroup> findByMemberIdAndGroup(Long memberId, Group group) {
        MemberGroup result = queryFactory
                .selectFrom(memberGroup)
                .where(
                        memberGroup.member.id.eq(memberId),
                        memberGroup.group.eq(group)
                )
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByMemberIdAndGroup(Long memberId, Group group) {
        Integer result = queryFactory
                .selectOne()
                .from(memberGroup)
                .where(
                        memberGroup.member.id.eq(memberId),
                        memberGroup.group.eq(group)
                )
                .fetchFirst();
        return result != null;
    }

    @Override
    public List<MemberGroup> findAllByMemberIdWithGroup(Long memberId) {
        return queryFactory
                .selectFrom(memberGroup)
                .join(memberGroup.group).fetchJoin()
                .where(memberGroup.member.id.eq(memberId))
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
