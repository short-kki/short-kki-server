package com.example.short_kki.domain.feed.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFeedRequest(
        @NotBlank(message = "피드 내용은 필수입니다.")
        @Size(max = 2000, message = "피드 내용은 2000자 이내여야 합니다.")
        String content
) {
}
