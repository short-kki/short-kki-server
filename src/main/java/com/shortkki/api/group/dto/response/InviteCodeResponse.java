package com.shortkki.api.group.dto.response;

import com.shortkki.api.group.entity.InviteLink;
import java.time.LocalDateTime;

public record InviteCodeResponse(
        String inviteCode,
        LocalDateTime expiresAt
) {

    public static InviteCodeResponse from(InviteLink inviteLink) {
        return new InviteCodeResponse(inviteLink.getCode(), inviteLink.getExpiresAt());
    }
}
