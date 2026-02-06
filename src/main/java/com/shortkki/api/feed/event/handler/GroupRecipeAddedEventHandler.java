package com.shortkki.api.feed.event.handler;

import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.FeedType;
import com.shortkki.api.feed.event.GroupRecipeAddedEvent;
import com.shortkki.api.feed.repository.FeedRepository;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupRecipeAddedEventHandler {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final FeedRepository feedRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(GroupRecipeAddedEvent event) {
        Group group = groupRepository.findById(event.groupId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_NOT_FOUND));
        Member member = memberRepository.findById(event.memberId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Recipe recipe = recipeRepository.findById(event.recipeId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_NOT_FOUND));

        Feed feed = Feed.create(group, member, "새 레시피를 추가했습니다.", FeedType.NEW_RECIPE_ADDED, recipe);
        feedRepository.save(feed);

        log.info("그룹 레시피 추가 피드 생성 완료. groupId={}, memberId={}, recipeId={}",
                event.groupId(), event.memberId(), event.recipeId());
    }
}
