package com.example.short_kki.domain.group.service;

import com.example.short_kki.domain.feed.dto.response.FeedResponse;
import com.example.short_kki.domain.feed.entity.Feed;
import com.example.short_kki.domain.feed.repository.FeedRepository;
import com.example.short_kki.domain.group.dto.request.CreateFeedRequest;
import com.example.short_kki.domain.group.dto.request.CreateGroupRequest;
import com.example.short_kki.domain.group.dto.request.JoinGroupRequest;
import com.example.short_kki.domain.group.dto.request.UpdateGroupRequest;
import com.example.short_kki.domain.group.dto.response.*;
import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.group.entity.MemberGroup;
import com.example.short_kki.domain.group.repository.GroupRepository;
import com.example.short_kki.domain.group.repository.MemberGroupRepository;
import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.repository.MemberRepository;
import com.example.short_kki.global.exception.BusinessException;
import com.example.short_kki.global.exception.ErrorCode;
import com.example.short_kki.global.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public void createGroup(Long memberId, CreateGroupRequest request) {
        Member member = findMemberById(memberId);
        Group group = Group.create(request.name(), request.description(), request.thumbnailImgUrl(),
                generateInviteCode());
        groupRepository.save(group);
        MemberGroup memberGroup = MemberGroup.createAdmin(member, group);
        memberGroupRepository.save(memberGroup);
    }

    @Transactional
    public void updateGroup(Long memberId, Long groupId, UpdateGroupRequest request) {
        Group group = findGroupById(groupId);
        MemberGroup memberGroup = findMemberGroup(memberId, group);
        validateAdminRole(memberGroup);
        group.updateGroupInfo(request.name(), request.description(), request.thumbnailImgUrl());
    }

    @Transactional
    public void deleteGroup(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        MemberGroup memberGroup = findMemberGroup(memberId, group);
        validateAdminRole(memberGroup);
        groupRepository.delete(group);
    }

    public GroupResponse getGroup(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        long memberCount = memberGroupRepository.countByGroup(group);
        return GroupResponse.of(group, memberCount);
    }

    public List<GroupListResponse> getMyGroups(Long memberId) {
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByMemberIdWithGroup(memberId);
        return memberGroups.stream()
                .map(mg -> GroupListResponse.of(mg.getGroup(), mg.getRole()))
                .toList();
    }

    public List<GroupMemberResponse> getGroupMembers(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByGroupWithMember(group);
        return memberGroups.stream()
                .map(GroupMemberResponse::from)
                .toList();
    }

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
        Feed feed = Feed.create(group, member, request.content());
        feedRepository.save(feed);
    }

    public List<Object> getShoppingList(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);

        // TODO: 장보기 테이블 구현 후 실제 데이터 조회 로직 추가
        return Collections.emptyList();
    }

    @Transactional
    public void deleteShoppingList(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        MemberGroup memberGroup = findMemberGroup(memberId, group);
        validateAdminRole(memberGroup);
        // TODO: 장보기 테이블 구현 후 삭제 로직 추가
    }

    @Transactional
    public GroupResponse joinGroup(Long memberId, JoinGroupRequest request) {
        Group group = groupRepository.findByCode(request.inviteCode())
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_INVALID_INVITE_CODE));
        existMemberGroupByMemberIdAndGroup(memberId, group);
        Member member = findMemberById(memberId);
        MemberGroup memberGroup = MemberGroup.createMember(member, group);
        memberGroupRepository.save(memberGroup);
        long memberCount = memberGroupRepository.countByGroup(group);
        return GroupResponse.of(group, memberCount);
    }

    private void existMemberGroupByMemberIdAndGroup(Long memberId, Group group) {
        if (memberGroupRepository.existsByMemberIdAndGroup(memberId, group)) {
            throw new BusinessException(ErrorCode.GROUP_ALREADY_JOINED);
        }
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private MemberGroup findMemberGroup(Long memberId, Group group) {
        return memberGroupRepository.findByMemberIdAndGroup(memberId, group)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_NOT_MEMBER));
    }

    private void validateAdminRole(MemberGroup memberGroup) {
        if (!memberGroup.isAdmin()) {
            throw new BusinessException(ErrorCode.GROUP_ADMIN_REQUIRED);
        }
    }

    private void validateGroupMember(Long memberId, Group group) {
        if (!memberGroupRepository.existsByMemberIdAndGroup(memberId, group)) {
            throw new BusinessException(ErrorCode.GROUP_NOT_MEMBER);
        }
    }

    private String generateInviteCode() {
        // TODO : 초대 코드 생성 관련 고민 더 하기
        String code;
        do {
            code = CodeGenerator.generateEventCode();
        } while (groupRepository.existsByCode(code));
        return code;
    }
}
