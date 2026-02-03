package com.shortkki.api.group.application.service;

import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberValidationService {

    private final GroupValidationService groupValidationService;

    private final GroupMemberRepository groupMemberRepository;

    public void validateGroupMember(long memberId, long groupId) {
        groupValidationService.validateGroupExist(groupId);

        if (!groupMemberRepository.existsByMemberIdAndGroup(memberId, groupId)) {
            throw new AccessDeniedException(ErrorCode.GROUP_NOT_MEMBER);
        }
    }
}
