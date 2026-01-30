package com.shortkki.api.group.service;

import com.shortkki.api.group.dto.request.CreateGroupRequest;
import com.shortkki.api.group.dto.request.JoinGroupRequest;
import com.shortkki.api.group.dto.request.UpdateGroupRequest;
import com.shortkki.api.group.dto.response.GroupListResponse;
import com.shortkki.api.group.dto.response.GroupMemberResponse;
import com.shortkki.api.group.dto.response.GroupPreviewResponse;
import com.shortkki.api.group.dto.response.GroupResponse;
import com.shortkki.api.group.dto.response.InviteCodeResponse;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupMember;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public GroupResponse createGroup(Long memberId, CreateGroupRequest request) {
        Member member = findMemberById(memberId);
        Group group = Group.create(
                request.name(),
                request.description(),
                request.thumbnailImgUrl(),
                request.groupType(),
                generateInviteCode());
        Group savedGroup = groupRepository.save(group);
        GroupMember groupMember = GroupMember.createAdmin(member, savedGroup);
        groupMemberRepository.save(groupMember);

        return GroupResponse.from(savedGroup, 1L);
    }

    @Transactional
    public void updateGroup(Long memberId, Long groupId, UpdateGroupRequest request) {
        Group group = findGroupById(groupId);
        GroupMember groupMember = findGroupMember(memberId, group);
        validateGroupMemberAdmin(groupMember);
        group.updateGroupInfo(
                request.name(),
                request.description(),
                request.thumbnailImgUrl(),
                request.groupType());
    }

    @Transactional
    public void deleteGroup(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        GroupMember groupMember = findGroupMember(memberId, group);
        validateGroupMemberAdmin(groupMember);
        groupRepository.delete(group);
    }

    public GroupResponse getGroup(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        long memberCount = groupMemberRepository.countByGroup(group);
        return GroupResponse.from(group, memberCount);
    }

    public List<GroupListResponse> getMyGroups(Long memberId) {
        List<GroupMember> groupMembers = groupMemberRepository.findAllByMemberIdWithGroup(memberId);
        return groupMembers.stream()
                .map(gm -> GroupListResponse.from(gm.getGroup(), gm.getRole()))
                .toList();
    }

    public List<GroupMemberResponse> getGroupMembers(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupWithMember(group);
        return groupMembers.stream()
                .map(GroupMemberResponse::from)
                .toList();
    }

    @Transactional
    public GroupResponse joinGroup(Long memberId, JoinGroupRequest request) {
        Group group = findGroupByInviteCode(request.inviteCode());
        existGroupMemberByMemberIdAndGroup(memberId, group);
        Member member = findMemberById(memberId);
        GroupMember groupMember = GroupMember.createMember(member, group);
        groupMemberRepository.save(groupMember);
        long memberCount = groupMemberRepository.countByGroup(group);
        return GroupResponse.from(group, memberCount);
    }

    public GroupPreviewResponse getGroupPreviewByInviteCode(String inviteCode) {
        Group group = findGroupByInviteCode(inviteCode);
        long memberCount = groupMemberRepository.countByGroup(group);
        return GroupPreviewResponse.from(group, memberCount);
    }

    public InviteCodeResponse getInviteCode(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        GroupMember groupMember = findGroupMember(memberId, group);
        validateGroupMemberAdmin(groupMember);
        return InviteCodeResponse.of(group.getCode());
    }

    private Group findGroupByInviteCode(String inviteCode) {
        return groupRepository.findByCode(inviteCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_INVALID_INVITE_CODE));
    }

    private void existGroupMemberByMemberIdAndGroup(Long memberId, Group group) {
        if (groupMemberRepository.existsByMemberIdAndGroup(memberId, group)) {
            throw new BusinessException(ErrorCode.GROUP_ALREADY_JOINED);
        }
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private GroupMember findGroupMember(Long memberId, Group group) {
        return groupMemberRepository.findByMemberIdAndGroup(memberId, group)
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.GROUP_NOT_MEMBER));
    }

    private void validateGroupMemberAdmin(GroupMember groupMember) {
        if (!groupMember.checkIsAdmin()) {
            throw new AccessDeniedException(ErrorCode.GROUP_ADMIN_REQUIRED);
        }
    }

    private void validateGroupMember(Long memberId, Group group) {
        if (!groupMemberRepository.existsByMemberIdAndGroup(memberId, group)) {
            throw new AccessDeniedException(ErrorCode.GROUP_NOT_MEMBER);
        }
    }

    private String generateInviteCode() {
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            String code = CodeGenerator.generateEventCode();
            if (!groupRepository.existsByCode(code)) {
                return code;
            }
        }
        throw new BusinessException(ErrorCode.GROUP_INVITE_CODE_GENERATION_FAILED);
    }
}
