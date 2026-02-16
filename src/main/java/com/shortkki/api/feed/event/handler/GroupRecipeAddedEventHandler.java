package com.shortkki.api.feed.event.handler;

import com.shortkki.api.feed.event.GroupRecipeAddedEvent;
import com.shortkki.api.feed.service.FeedService;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.notification.event.NotificationEvent;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.event.DomainEventPublisher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupRecipeAddedEventHandler {

    private final FeedService feedService;
    private final GroupMemberRepository groupMemberRepository;
    private final RecipeRepository recipeRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(GroupRecipeAddedEvent event) {
        try {
            // 1. 피드 생성 (새 트랜잭션에서 실행)
            feedService.createRecipeAddedFeed(event.groupId(), event.memberId(), event.recipeId());

            // 2. 그룹원들에게 알림 발송
            publishRecipeSharedNotification(event.groupId(), event.memberId(), event.recipeId());
        } catch (Exception e) {
            log.error("그룹 레시피 추가 이벤트 처리 실패. groupId={}, memberId={}, recipeId={}, error={}",
                    event.groupId(), event.memberId(), event.recipeId(), e.getMessage());
        }
    }

    private void publishRecipeSharedNotification(Long groupId, Long senderId, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_NOT_FOUND));

        List<Long> receiverIds = groupMemberRepository.findMemberIdsByGroupId(groupId)
                .stream()
                .filter(id -> !id.equals(senderId))
                .toList();

        if (!receiverIds.isEmpty()) {
            domainEventPublisher.publish(
                    NotificationEvent.recipeShared(
                            receiverIds,
                            senderId,
                            recipe.getId(),
                            recipe.getBasicInfo().getTitle(),
                            groupId
                    )
            );
            log.info("레시피 공유 알림 이벤트 발행. receiverIds={}, recipeId={}", receiverIds, recipeId);
        }
    }
}
