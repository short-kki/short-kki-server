package com.example.short_kki.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;

public record JoinGroupRequest(
        @NotBlank(message = "초대 코드는 필수입니다.")
        String inviteCode
) {
}
