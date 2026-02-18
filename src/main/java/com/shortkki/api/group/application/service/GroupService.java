package com.shortkki.api.group.application.service;


import com.shortkki.api.calendar.repository.RecipeCalendarRepository;
import com.shortkki.api.feed.repository.FeedRepository;
import com.shortkki.api.group.dto.request.CreateGroupRequest;
import com.shortkki.api.group.dto.request.UpdateGroupRequest;
import com.shortkki.api.group.dto.response.GroupListResponse;
import com.shortkki.api.group.dto.response.GroupMemberResponse;
import com.shortkki.api.group.dto.response.GroupPreviewResponse;
import com.shortkki.api.group.dto.response.GroupResponse;
import com.shortkki.api.group.dto.response.InviteCodeResponse;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupMember;
import com.shortkki.api.group.entity.InviteLink;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.group.repository.InviteLinkRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.shopping_list.repository.ShoppingListRepository;
import com.shortkki.api.notification.event.NotificationEvent;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.InternalServerException;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.event.DomainEventPublisher;
import com.shortkki.global.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupMemberValidationService groupMemberValidationService;

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;
    private final InviteLinkRepository inviteLinkRepository;
    private final FeedRepository feedRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final RecipeCalendarRepository recipeCalendarRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public GroupResponse createGroup(Long memberId, CreateGroupRequest request) {
        Member member = findMemberById(memberId);
        Group group = Group.create(
                request.name(),
                request.description(),
                request.thumbnailImgUrl(),
                request.groupType()
        );
        Group savedGroup = groupRepository.save(group);
        GroupMember groupMember = GroupMember.createAdmin(member, group);
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
        // TODO(#67)
        // recipe.bookmarkCount 보정 로직 연결 필요
        // - 삭제 대상 recipeIds 수집
        // - 다른 스코프(개인/다른 그룹)에서 잔존 여부 확인
        // - 잔존하지 않는 recipeIds만 decrementBookmarkCountBulk
        feedRepository.deleteAllByGroup(group);
        shoppingListRepository.deleteAllByGroup(group);
        recipeCalendarRepository.deleteAllByGroup(group);
        inviteLinkRepository.deleteAllByGroup(group);
        groupMemberRepository.deleteAllByGroup(group);
        groupRepository.delete(group);
    }

    public GroupResponse getGroup(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        long memberCount = groupMemberRepository.countByGroup(group);
        return GroupResponse.from(group, memberCount);
    }

    public List<GroupListResponse> getMyGroups(Long memberId) {
        List<GroupMember> groupMembers = groupMemberRepository.findAllByMemberIdWithGroup(memberId);
        if (groupMembers.isEmpty()) {
            return List.of();
        }

        List<Long> groupIds = groupMembers.stream()
                .map(gm -> gm.getGroup().getId())
                .toList();
        Map<Long, Long> memberCountMap = groupMemberRepository.countByGroupIds(groupIds);
        Map<Long, LocalDateTime> lastFeedAtMap = feedRepository.findLatestCreatedAtByGroupIds(groupIds);

        return groupMembers.stream()
                .map(gm -> GroupListResponse.from(
                        gm.getGroup(),
                        gm.getRole(),
                        memberCountMap.getOrDefault(gm.getGroup().getId(), 0L),
                        lastFeedAtMap.get(gm.getGroup().getId())
                ))
                .toList();
    }

    public List<GroupMemberResponse> getGroupMembers(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupWithMember(group);
        return groupMembers.stream()
                .map(GroupMemberResponse::from)
                .toList();
    }

    @Transactional
    public GroupResponse joinGroup(Long memberId, String inviteCode) {
        Member member = findMemberById(memberId);

        InviteLink inviteLink = findInviteLinkByCode(inviteCode);
        validateInviteLinkNotExpired(inviteLink);

        Group group = inviteLink.getGroup();
        groupMemberValidationService.validateNotAlreadyJoined(memberId, group.getId());

        GroupMember groupMember = GroupMember.createMember(member, group);
        groupMemberRepository.save(groupMember);

        // 기존 그룹원들에게 새 멤버 가입 알림
        publishMemberJoinedNotification(group, member);

        long memberCount = groupMemberRepository.countByGroup(group);
        return GroupResponse.from(group, memberCount);
    }

    private void publishMemberJoinedNotification(Group group, Member joinedMember) {
        List<Long> receiverIds = groupMemberRepository.findMemberIdsByGroupId(group.getId())
                .stream()
                .filter(id -> !id.equals(joinedMember.getId()))
                .toList();

        if (!receiverIds.isEmpty()) {
            domainEventPublisher.publish(
                    NotificationEvent.memberJoined(
                            receiverIds,
                            group.getId(),
                            joinedMember.getName()
                    )
            );
        }
    }

    public GroupPreviewResponse getGroupPreviewByInviteCode(String inviteCode) {
        InviteLink inviteLink = findInviteLinkByCode(inviteCode);
        validateInviteLinkNotExpired(inviteLink);
        Group group = inviteLink.getGroup();
        long memberCount = groupMemberRepository.countByGroup(group);
        return GroupPreviewResponse.from(group, memberCount);
    }

    @Transactional
    public InviteCodeResponse getOrGenerateInviteCode(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        InviteLink inviteLink = getOrCreateValidInviteLink(group);
        return InviteCodeResponse.from(inviteLink);
    }

    @Transactional
    public void kickMember(Long requesterId, Long groupId, Long targetMemberId) {
        Group group = findGroupById(groupId);
        GroupMember requesterGroupMember = findGroupMember(requesterId, group);
        validateGroupMemberAdmin(requesterGroupMember);
        validateNotKickingSelf(requesterId, targetMemberId);
        GroupMember targetGroupMember = findGroupMemberByMemberIdAndGroup(targetMemberId, group);
        groupMemberRepository.delete(targetGroupMember);
    }

    @Transactional
    public void leaveGroup(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        GroupMember groupMember = findGroupMember(memberId, group);
        validateNotAdmin(groupMember);
        groupMemberRepository.delete(groupMember);
    }

    private void validateNotAdmin(GroupMember groupMember) {
        if (groupMember.checkIsAdmin()) {
            throw new BadRequestException(ErrorCode.GROUP_ADMIN_CANNOT_LEAVE);
        }
    }

    private InviteLink createInviteLink(Group group) {
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            String code = CodeGenerator.generateEventCode();
            if (!inviteLinkRepository.existsByCode(code)) {
                InviteLink inviteLink = InviteLink.create(group, code);
                return inviteLinkRepository.save(inviteLink);
            }
        }
        throw new InternalServerException(ErrorCode.GROUP_INVITE_CODE_GENERATION_FAILED);
    }

    private InviteLink findInviteLinkByCode(String inviteCode) {
        return inviteLinkRepository.findByCode(inviteCode)
                .orElseThrow(() -> new BadRequestException(ErrorCode.GROUP_INVALID_INVITE_CODE));
    }

    private void validateInviteLinkNotExpired(InviteLink inviteLink) {
        if (inviteLink.isExpired()) {
            throw new BadRequestException(ErrorCode.GROUP_INVITE_LINK_EXPIRED);
        }
    }

    private void validateNotKickingSelf(Long requesterId, Long targetMemberId) {
        if (requesterId.equals(targetMemberId)) {
            throw new BadRequestException(ErrorCode.GROUP_CANNOT_KICK_SELF);
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

    private GroupMember findGroupMemberByMemberIdAndGroup(Long memberId, Group group) {
        return groupMemberRepository.findByMemberIdAndGroup(memberId, group)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_MEMBER_NOT_FOUND));
    }

    private void validateGroupMemberAdmin(GroupMember groupMember) {
        if (!groupMember.checkIsAdmin()) {
            throw new AccessDeniedException(ErrorCode.GROUP_ADMIN_REQUIRED);
        }
    }

    private InviteLink getOrCreateValidInviteLink(Group group) {
        return inviteLinkRepository.findValidLinkByGroup(group)
                .orElseGet(() -> createInviteLink(group));
    }
}
