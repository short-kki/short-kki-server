package com.shortkki.api.feed.service;

import com.shortkki.api.feed.dto.request.CreateFeedRequest;
import com.shortkki.api.feed.dto.response.FeedResponse;
import com.shortkki.api.feed.entity.Feed;
import com.shortkki.api.feed.entity.FeedLike;
import com.shortkki.api.feed.repository.FeedLikeRepository;
import com.shortkki.api.feed.repository.FeedRepository;
import com.shortkki.api.feed.entity.FeedType;
import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.file.entity.FileTargetType;
import com.shortkki.api.file.repository.FileMetadataRepository;
import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.ConflictException;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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
    private final FileMetadataRepository fileMetadataRepository;
    private final FileMetadataQueryService fileMetadataQueryService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;

    public List<FeedResponse> getGroupFeeds(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        List<Feed> feeds = feedRepository.findAllByGroupWithMember(group);
        Set<Long> likedFeedIds = feedLikeRepository.findLikedFeedIdsByMemberIdAndFeedIn(memberId, feeds);
        return feeds.stream()
                .map(feed -> {
                    log.info(feed.getImageUrl());
                    return FeedResponse.from(feed, likedFeedIds.contains(feed.getId()));
                })
                .toList();
    }

    @Transactional
    public void createFeed(Long memberId, Long groupId, CreateFeedRequest request) {
        Member member = findMemberById(memberId);
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());

        Feed feed;
        if (request.feedType() == FeedType.NEW_RECIPE_ADDED) {
            Recipe recipe = recipeRepository.findById(request.recipeId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_NOT_FOUND));
            feed = Feed.create(group, member, request.content(), request.feedType(), recipe);
        } else {
            FileMetadata image = null;
            if (request.imageFileId() != null) {
                image = fileMetadataQueryService.findByIdWithOwnerValidation(request.imageFileId(), memberId);
            }
            feed = Feed.create(group, member, request.content(), request.feedType(), image);
            feedRepository.save(feed);
            if (image != null) {
                image.bindTarget(FileTargetType.FEED_IMG, feed.getId());
            }
            return;
        }
        feedRepository.save(feed);
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
            fileMetadataRepository.delete(feed.getImage());
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
