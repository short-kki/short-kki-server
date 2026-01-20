package com.example.short_kki.domain.group.entity;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.global.entity.BaseEntity;
import com.example.short_kki.global.exception.BusinessException;
import com.example.short_kki.global.exception.ErrorCode;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupRole role;

    // TODO : Member 쪽 단방향 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

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

    public void isAdmin() {
        if (this.role != GroupRole.ADMIN) {
            throw new BusinessException(ErrorCode.GROUP_ADMIN_REQUIRED);
        }
    }
}