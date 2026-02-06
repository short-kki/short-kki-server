package com.shortkki.api.feed.dto.request;

import com.shortkki.api.feed.entity.FeedType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFeedRequest(
        @NotBlank(message = "피드 내용은 필수입니다.")
        @Size(max = 2000, message = "피드 내용은 2000자 이내여야 합니다.")
        String content,

        @NotNull(message = "피드 타입은 필수입니다.")
        FeedType feedType,

        Long imageFileId
) {
}
