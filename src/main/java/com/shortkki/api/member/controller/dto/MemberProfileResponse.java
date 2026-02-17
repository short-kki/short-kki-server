package com.shortkki.api.member.controller.dto;

import com.shortkki.api.member.entity.Member;

public record MemberProfileResponse(
        Long id,
        String email,
        String name,
        Long profileImgFileId,
        String profileImgUrl
) {
    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getProfileImgFile() != null ? member.getProfileImgFile().getId() : null,
                member.getProfileImgUrl()
        );
    }
}
