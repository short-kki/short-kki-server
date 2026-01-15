package com.example.short_kki.domain.group.dto.response;

import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.entity.GroupType;

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
    public static GroupResponse of(Group group, long memberCount) {
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
