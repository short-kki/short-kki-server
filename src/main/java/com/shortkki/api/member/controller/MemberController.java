package com.shortkki.api.member.controller;

import com.shortkki.api.member.dto.request.UpdateProfileRequest;
import com.shortkki.api.member.dto.response.MemberProfileResponse;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.service.MemberQueryService;
import com.shortkki.api.member.service.MemberService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "회원 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberService memberService;

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/profile")
    public ResponseEntity<BaseResponse<MemberProfileResponse>> getMyProfile(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Member member = memberQueryService.findMemberWithProfile(loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success(MemberProfileResponse.from(member)));
    }

    @Operation(summary = "내 프로필 수정")
    @PatchMapping("/profile")
    public ResponseEntity<BaseResponse<Void>> updateMyProfile(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        memberService.updateProfile(
                loginMember.getId(),
                request.name(),
                request.profileImgFileId()
        );
        return ResponseEntity.ok(BaseResponse.success());
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> withdraw(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        memberService.withdraw(loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success());
    }
}
