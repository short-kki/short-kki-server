package com.shortkki.api.contentFeedback.dto.response;

import com.shortkki.api.contentFeedback.entity.ContentFeedback;
import com.shortkki.api.contentFeedback.entity.FeedbackTargetType;
import com.shortkki.api.contentFeedback.entity.FeedbackType;

import java.time.LocalDateTime;

public record ContentFeedbackResponse(
        Long id,
        Long memberId,
        String memberName,
        FeedbackTargetType targetType,
        Long targetId,
        FeedbackType feedbackType,
        String description,
        boolean resolved,
        LocalDateTime createdAt
) {

    public static ContentFeedbackResponse from(ContentFeedback feedback) {
        return new ContentFeedbackResponse(
                feedback.getId(),
                feedback.getMember().getId(),
                feedback.getMember().getName(),
                feedback.getTargetType(),
                feedback.getTargetId(),
                feedback.getFeedbackType(),
                feedback.getDescription(),
                feedback.isResolved(),
                feedback.getCreatedAt()
        );
    }
}
