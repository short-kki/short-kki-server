package com.shortkki.api.group.service;

import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupValidationService {

    private final GroupRepository groupRepository;

    public void validateGroupExist(long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new AccessDeniedException(ErrorCode.GROUP_NOT_FOUND);
        }
    }
}
