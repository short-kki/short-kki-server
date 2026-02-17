package com.shortkki.api.group.dto.response;

import com.shortkki.api.group.entity.GroupMember;
import com.shortkki.api.group.entity.GroupRole;

import java.time.LocalDateTime;

public record GroupMemberResponse(
        Long memberId,
        String name,
        String email,
        String profileImgUrl,
        GroupRole role,
        LocalDateTime joinedAt
) {

    public static GroupMemberResponse from(GroupMember groupMember) {
        return new GroupMemberResponse(
                groupMember.getMember().getId(),
                groupMember.getMember().getName(),
                groupMember.getMember().getEmail(),
                groupMember.getMember().getProfileImgUrl(),
                groupMember.getRole(),
                groupMember.getCreatedAt()
        );
    }
}
