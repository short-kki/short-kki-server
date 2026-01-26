package com.shortkki.api.group.dto.response;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupRole;
import com.shortkki.api.group.entity.GroupType;

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

    public static GroupListResponse from(Group group, GroupRole myRole) {
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
