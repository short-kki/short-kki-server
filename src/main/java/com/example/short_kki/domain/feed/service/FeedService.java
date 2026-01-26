package com.example.short_kki.domain.feed.service;

import com.example.short_kki.domain.feed.dto.request.CreateFeedRequest;
import com.example.short_kki.domain.feed.dto.response.FeedResponse;
import com.example.short_kki.domain.feed.entity.Feed;
import com.example.short_kki.domain.feed.repository.FeedRepository;
import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.repository.GroupRepository;
import com.example.short_kki.domain.group.repository.GroupMemberRepository;
import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.repository.MemberRepository;
import com.example.short_kki.global.error.ErrorCode;
import com.example.short_kki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

    private final FeedRepository feedRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;

    public List<FeedResponse> getGroupFeeds(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        List<Feed> feeds = feedRepository.findAllByGroupWithMember(group);
        return feeds.stream()
                .map(FeedResponse::from)
                .toList();
    }

    @Transactional
    public void createFeed(Long memberId, Long groupId, CreateFeedRequest request) {
        Member member = findMemberById(memberId);
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        Feed feed = Feed.create(group, member, request.content(), request.feedType());
        feedRepository.save(feed);
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateGroupMember(Long memberId, Group group) {
        if (!groupMemberRepository.existsByMemberIdAndGroup(memberId, group)) {
            throw new NotFoundException(ErrorCode.GROUP_NOT_MEMBER);
        }
    }
}
