package com.shortkki.api.group.dto.response;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupType;

public record GroupPreviewResponse(
        Long id,
        String name,
        String description,
        String thumbnailImgUrl,
        GroupType groupType,
        long memberCount
) {

    public static GroupPreviewResponse from(Group group, long memberCount) {
        return new GroupPreviewResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getThumbnailImgUrl(),
                group.getGroupType(),
                memberCount
        );
    }
}
