package com.shortkki.api.contentFeedback.service;

import com.shortkki.api.contentFeedback.dto.request.CreateContentFeedbackRequest;
import com.shortkki.api.contentFeedback.entity.ContentFeedback;
import com.shortkki.api.contentFeedback.entity.FeedbackTargetType;
import com.shortkki.api.contentFeedback.repository.ContentFeedbackRepository;
import com.shortkki.api.feed.repository.FeedRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentFeedbackService {

    private final ContentFeedbackRepository contentFeedbackRepository;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public void createFeedback(Long memberId, CreateContentFeedbackRequest request) {
        Member member = findMemberById(memberId);
        validateTargetExists(request.targetType(), request.targetId());

        ContentFeedback feedback = ContentFeedback.create(
                member,
                request.targetType(),
                request.targetId(),
                request.feedbackType(),
                request.description()
        );

        contentFeedbackRepository.save(feedback);
    }

    private void validateTargetExists(FeedbackTargetType targetType, Long targetId) {
        switch (targetType) {
            case RECIPE -> {
                if (!recipeRepository.existsById(targetId)) {
                    throw new NotFoundException(ErrorCode.RECIPE_NOT_FOUND);
                }
            }
            case FEED -> {
                if (!feedRepository.existsById(targetId)) {
                    throw new NotFoundException(ErrorCode.NOT_FOUND_ERROR);
                }
            }
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
