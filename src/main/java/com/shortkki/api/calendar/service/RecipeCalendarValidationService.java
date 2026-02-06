package com.shortkki.api.calendar.service;

import com.shortkki.api.calendar.entity.RecipeCalendar;
import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeCalendarValidationService {

    private final GroupMemberValidationService groupMemberValidationService;

    public void validatePersonalCalendarOwner(RecipeCalendar calendar, long memberId) {
        if (calendar.getGroup() != null) {
            throw new BadRequestException("개인 캘린더가 아닙니다.");
        }

        Member owner = calendar.getMember();
        if (owner == null || !owner.getId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    public void validateGroupCalendarAccess(RecipeCalendar calendar, long memberId, long groupId) {
        if (calendar.getGroup() == null) {
            throw new BadRequestException("그룹 캘린더가 아닙니다.");
        }

        if (!calendar.getGroup().getId().equals(groupId)) {
            throw new BadRequestException("해당 그룹의 캘린더가 아닙니다.");
        }

        groupMemberValidationService.validateGroupMember(memberId, groupId);
    }
}
