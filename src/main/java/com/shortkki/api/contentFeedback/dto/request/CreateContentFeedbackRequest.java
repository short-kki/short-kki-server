package com.shortkki.api.contentFeedback.dto.request;

import com.shortkki.api.contentFeedback.entity.FeedbackTargetType;
import com.shortkki.api.contentFeedback.entity.FeedbackType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateContentFeedbackRequest(
        @NotNull(message = "신고 대상 유형은 필수입니다.")
        FeedbackTargetType targetType,

        @NotNull(message = "신고 대상 ID는 필수입니다.")
        Long targetId,

        @NotNull(message = "신고 유형은 필수입니다.")
        FeedbackType feedbackType,

        @Size(max = 500, message = "상세 내용은 500자 이내여야 합니다.")
        String description
) {
}
