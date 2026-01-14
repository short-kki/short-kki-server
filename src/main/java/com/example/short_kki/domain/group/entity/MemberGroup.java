package com.example.short_kki.domain.group.entity;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_group")
public class MemberGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO : Member 쪽 단방향 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupRole role;

    @Builder
    private MemberGroup(Member member, Group group, GroupRole role) {
        this.member = member;
        this.group = group;
        this.role = role;
    }

    public static MemberGroup createAdmin(Member member, Group group) {
        return MemberGroup.builder()
                .member(member)
                .group(group)
                .role(GroupRole.ADMIN)
                .build();
    }

    public static MemberGroup createMember(Member member, Group group) {
        return MemberGroup.builder()
                .member(member)
                .group(group)
                .role(GroupRole.MEMBER)
                .build();
    }

    public boolean isAdmin() {
        return this.role == GroupRole.ADMIN;
    }
}