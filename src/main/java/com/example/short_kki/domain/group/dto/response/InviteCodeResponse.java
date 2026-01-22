package com.example.short_kki.domain.group.dto.response;

public record InviteCodeResponse(
        String inviteCode
) {
    public static InviteCodeResponse of(String inviteCode) {
        return new InviteCodeResponse(inviteCode);
    }
}
