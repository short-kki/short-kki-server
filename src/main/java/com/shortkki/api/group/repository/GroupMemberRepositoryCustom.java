package com.shortkki.api.group.repository;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupMember;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepositoryCustom {

    Optional<GroupMember> findByMemberIdAndGroup(Long memberId, Group group);

    boolean existsByMemberIdAndGroup(Long memberId, Group group);

    List<GroupMember> findAllByMemberIdWithGroup(Long memberId);

    List<GroupMember> findAllByGroupWithMember(Group group);

    long countByGroup(Group group);
}
