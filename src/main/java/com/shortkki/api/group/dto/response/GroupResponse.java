package com.shortkki.api.group.dto.response;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupType;

import java.time.LocalDateTime;

public record GroupResponse(
        Long id,
        String name,
        String description,
        String thumbnailImgUrl,
        GroupType groupType,
        String code,
        long memberCount,
        LocalDateTime createdAt
) {

    public static GroupResponse from(Group group, long memberCount) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getThumbnailImgUrl(),
                group.getGroupType(),
                group.getCode(),
                memberCount,
                group.getCreatedAt()
        );
    }
}
