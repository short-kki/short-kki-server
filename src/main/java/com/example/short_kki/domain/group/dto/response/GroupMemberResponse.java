package com.example.short_kki.domain.group.dto.response;

import com.example.short_kki.domain.group.entity.GroupRole;
import com.example.short_kki.domain.group.entity.MemberGroup;

import java.time.LocalDateTime;

public record GroupMemberResponse(
        Long memberId,
        String name,
        String email,
        GroupRole role,
        LocalDateTime joinedAt
) {
    public static GroupMemberResponse from(MemberGroup memberGroup) {
        return new GroupMemberResponse(
                memberGroup.getMember().getId(),
                memberGroup.getMember().getName(),
                memberGroup.getMember().getEmail(),
                memberGroup.getRole(),
                memberGroup.getCreatedAt()
        );
    }
}
