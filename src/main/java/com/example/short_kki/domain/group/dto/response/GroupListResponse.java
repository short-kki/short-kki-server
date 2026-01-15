package com.example.short_kki.domain.group.dto.response;

import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.entity.GroupRole;
import com.example.short_kki.domain.group.entity.GroupType;

import java.time.LocalDateTime;

public record GroupListResponse(
        Long id,
        String name,
        String description,
        String thumbnailImgUrl,
        GroupType groupType,
        GroupRole myRole,
        LocalDateTime createdAt
) {
    public static GroupListResponse of(Group group, GroupRole myRole) {
        return new GroupListResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getThumbnailImgUrl(),
                group.getGroupType(),
                myRole,
                group.getCreatedAt()
        );
    }
}
