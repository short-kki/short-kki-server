package com.example.short_kki.domain.group.repository;

import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.entity.MemberGroup;

import java.util.List;
import java.util.Optional;

public interface MemberGroupRepositoryCustom {

    Optional<MemberGroup> findByMemberIdAndGroup(Long memberId, Group group);

    boolean existsByMemberIdAndGroup(Long memberId, Group group);

    List<MemberGroup> findAllByMemberIdWithGroup(Long memberId);

    List<MemberGroup> findAllByGroupWithMember(Group group);

    long countByGroup(Group group);
}
