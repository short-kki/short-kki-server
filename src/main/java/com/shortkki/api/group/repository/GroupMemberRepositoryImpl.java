package com.shortkki.api.group.repository;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupMember;
import com.shortkki.api.group.entity.QGroupMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.querydsl.core.Tuple;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryImpl implements GroupMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QGroupMember groupMember = QGroupMember.groupMember;

    @Override
    public Optional<GroupMember> findByMemberIdAndGroup(Long memberId, Group group) {
        GroupMember result = queryFactory
                .selectFrom(groupMember)
                .where(
                        groupMember.member.id.eq(memberId),
                        groupMember.group.eq(group)
                )
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByMemberIdAndGroup(Long memberId, Long groupId) {
        Integer result = queryFactory
                .selectOne()
                .from(groupMember)
                .where(
                        groupMember.member.id.eq(memberId),
                        groupMember.group.id.eq(groupId)
                )
                .fetchFirst();
        return result != null;
    }

    @Override
    public List<GroupMember> findAllByMemberIdWithGroup(Long memberId) {
        return queryFactory
                .selectFrom(groupMember)
                .join(groupMember.group).fetchJoin()
                .where(groupMember.member.id.eq(memberId))
                .orderBy(groupMember.group.createdAt.desc())
                .fetch();
    }

    @Override
    public List<GroupMember> findAllByGroupWithMember(Group group) {
        return queryFactory
                .selectFrom(groupMember)
                .join(groupMember.member).fetchJoin()
                .leftJoin(groupMember.member.profileImgFile).fetchJoin()
                .where(groupMember.group.eq(group))
                .fetch();
    }

    @Override
    public long countByGroup(Group group) {
        Long count = queryFactory
                .select(groupMember.count())
                .from(groupMember)
                .where(groupMember.group.eq(group))
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public Map<Long, Long> countByGroupIds(List<Long> groupIds) {
        List<Tuple> results = queryFactory
                .select(groupMember.group.id, groupMember.count())
                .from(groupMember)
                .where(groupMember.group.id.in(groupIds))
                .groupBy(groupMember.group.id)
                .fetch();

        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(groupMember.group.id),
                        tuple -> tuple.get(groupMember.count())
                ));
    }

    @Override
    public List<Long> findMemberIdsByGroupId(Long groupId) {
        return queryFactory
                .select(groupMember.member.id)
                .from(groupMember)
                .where(groupMember.group.id.eq(groupId))
                .fetch();
    }
}
