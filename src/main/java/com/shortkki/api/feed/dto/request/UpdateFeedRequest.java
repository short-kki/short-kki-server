package com.shortkki.api.feed.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateFeedRequest(
        @NotBlank(message = "피드 내용은 필수입니다.")
        String content
) {
}
