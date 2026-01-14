package com.example.short_kki.domain.group.repository;

import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.entity.MemberGroup;
import com.example.short_kki.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberGroupRepositoryCustom {

    Optional<MemberGroup> findByMemberAndGroup(Member member, Group group);

    boolean existsByMemberAndGroup(Member member, Group group);

    List<MemberGroup> findAllByMemberWithGroup(Member member);

    List<MemberGroup> findAllByGroupWithMember(Group group);

    long countByGroup(Group group);
}
