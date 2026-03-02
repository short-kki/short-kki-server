package com.shortkki.api.feed.service;

import com.shortkki.api.feed.dto.request.CreateFeedRequest;
import com.shortkki.api.feed.dto.request.UpdateFeedRequest;
import com.shortkki.api.feed.dto.response.FeedResponse;
import com.shortkki.api.feed.dto.response.FeedSliceResponse;
import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.FeedLike;
import com.shortkki.api.feed.entity.FeedType;
import com.shortkki.api.feed.repository.FeedLikeRepository;
import com.shortkki.api.feed.repository.FeedRepository;
import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.notification.event.NotificationEvent;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.ConflictException;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.event.DomainEventPublisher;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

    private final GroupMemberValidationService groupMemberValidationService;
    private final GroupMemberRepository groupMemberRepository;
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final FileMetadataQueryService fileMetadataQueryService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final DomainEventPublisher domainEventPublisher;

    public FeedSliceResponse getGroupFeeds(Long memberId, Long groupId, Long cursor, int size) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        Slice<Feed> feedSlice = feedRepository.findByGroupWithCursor(group, cursor, PageRequest.of(0, size));
        Set<Long> likedFeedIds = feedLikeRepository.findLikedFeedIdsByMemberIdAndFeedIn(memberId, feedSlice.getContent());
        return FeedSliceResponse.from(feedSlice, likedFeedIds);
    }

    public FeedResponse getFeed(Long memberId, Long groupId, Long feedId) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        Feed feed = findFeedByIdWithMember(feedId);
        validateFeedBelongsToGroup(feed, group);
        boolean isLiked = feedLikeRepository.existsByFeedAndMemberId(feed, memberId);
        return FeedResponse.from(feed, isLiked);
    }

    @Transactional
    public void updateFeed(Long memberId, Long groupId, Long feedId, UpdateFeedRequest request) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, groupId);
        Feed feed = findFeedById(feedId);
        validateFeedOwner(memberId, feed);
        validateFeedBelongsToGroup(feed, group);

        feed.updateContent(request.content());

        Long currentImageId = feed.getImage() != null ? feed.getImage().getId() : null;
        Long requestImageId = request.imageFileId();

        // 이미지 변경 없음
        if (java.util.Objects.equals(currentImageId, requestImageId)) {
            return;
        }

        // 이미지 삭제 (requestImageId가 null이고 기존 이미지가 있는 경우)
        if (requestImageId == null && currentImageId != null) {
            feed.getImage().markDeleted();
            feed.removeImage();
            return;
        }

        // 이미지 추가/교체
        if (requestImageId != null) {
            if (feed.getImage() != null) {
                feed.getImage().markDeleted();
            }
            FileMetadata newImage = fileMetadataQueryService.findByIdWithOwnerValidation(requestImageId, memberId);
            feed.updateImage(newImage);
            newImage.bindTarget(FileTargetType.FEED_IMG, feed.getId());
        }
    }

    @Transactional
    public void createFeed(Long memberId, Long groupId, CreateFeedRequest request) {
        Member member = findMemberById(memberId);
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        FileMetadata image = null;
        if (request.imageFileId() != null) {
            image = fileMetadataQueryService.findByIdWithOwnerValidation(request.imageFileId(), memberId);
        }
        Feed feed = Feed.create(group, member, request.content(), request.feedType(), image);
        feedRepository.save(feed);
        if (image != null) {
            image.bindTarget(FileTargetType.FEED_IMG, feed.getId());
        }
        publishFeedAddedNotification(groupId, memberId, feed.getId(), member.getName());
    }

    private void publishFeedAddedNotification(Long groupId, Long memberId, Long feedId, String memberName) {
        List<Long> receiverIds = groupMemberRepository.findMemberIdsByGroupId(groupId)
                .stream()
                .filter(id -> !id.equals(memberId))
                .toList();

        if (!receiverIds.isEmpty()) {
            domainEventPublisher.publish(
                    NotificationEvent.feedAdded(receiverIds, feedId, memberName, groupId)
            );
        }
    }

    @Transactional
    public void createRecipeAddedFeed(Long groupId, Long memberId, Long recipeId) {
        Group group = findGroupById(groupId);
        Member member = findMemberById(memberId);
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_NOT_FOUND));

        Feed feed = Feed.create(group, member, "새 레시피를 추가했습니다.", FeedType.NEW_RECIPE_ADDED, recipe);
        feedRepository.save(feed);

        log.info("그룹 레시피 추가 피드 생성 완료. groupId={}, memberId={}, recipeId={}",
                groupId, memberId, recipeId);
    }

    // TODO : FileUploadPort에 deleteFile(String objectKey) 메서드 추가
    @Transactional
    public void deleteFeed(Long memberId, Long groupId, Long feedId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, groupId);
        Feed feed = findFeedById(feedId);
        validateFeedOwner(memberId, feed);
        validateFeedBelongsToGroup(feed, group);
        feedLikeRepository.deleteByFeed(feed);
        if (feed.getImage() != null) {
            feed.getImage().markDeleted();
        }
        feedRepository.delete(feed);
    }

    @Transactional
    public void likeFeed(Long memberId, Long groupId, Long feedId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, groupId);
        Feed feed = findFeedById(feedId);
        validateFeedBelongsToGroup(feed, group);
        Member member = findMemberById(memberId);
        existsByFeedAndMember(feed, member);
        FeedLike feedLike = FeedLike.create(feed, member);
        feedLikeRepository.save(feedLike);
        feed.incrementLikes();
    }

    @Transactional
    public void unlikeFeed(Long memberId, Long groupId, Long feedId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, groupId);
        Feed feed = findFeedById(feedId);
        validateFeedBelongsToGroup(feed, group);
        Member member = findMemberById(memberId);
        FeedLike feedLike = findFeedLike(feed, member);
        feedLikeRepository.delete(feedLike);
        feed.decrementLikes();
    }

    private Feed findFeedById(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
    }

    private Feed findFeedByIdWithMember(Long feedId) {
        return feedRepository.findByIdWithMember(feedId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
    }

    private FeedLike findFeedLike(Feed feed, Member member) {
        return feedLikeRepository.findByFeedAndMember(feed, member)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEED_LIKE_NOT_FOUND));
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateGroupMember(Long memberId, Long groupId) {
        if (!groupMemberRepository.existsByMemberIdAndGroup(memberId, groupId)) {
            throw new AccessDeniedException(ErrorCode.GROUP_NOT_MEMBER);
        }
    }

    private void validateFeedOwner(Long memberId, Feed feed) {
        if (!feed.getMember().getId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void validateFeedBelongsToGroup(Feed feed, Group group) {
        if (!feed.getGroup().getId().equals(group.getId())) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void existsByFeedAndMember(Feed feed, Member member) {
        if (feedLikeRepository.existsByFeedAndMember(feed, member)) {
            throw new ConflictException(ErrorCode.FEED_ALREADY_LIKED);
        }
    }
}
