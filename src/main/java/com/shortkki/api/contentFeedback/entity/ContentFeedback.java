package com.shortkki.api.contentFeedback.entity;

import com.shortkki.api.member.entity.Member;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_feedback")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ContentFeedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackTargetType targetType;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackType feedbackType;

    @Column(length = 550)
    private String description;

    @Builder.Default
    @Column(nullable = false)
    private boolean resolved = false;

    public static ContentFeedback create(
            Member member, FeedbackTargetType targetType, Long targetId, FeedbackType feedbackType, String description
    ) {
        return ContentFeedback.builder()
                .member(member)
                .targetType(targetType)
                .targetId(targetId)
                .feedbackType(feedbackType)
                .description(description)
                .build();
    }
}
