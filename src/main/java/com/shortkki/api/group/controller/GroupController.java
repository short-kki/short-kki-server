package com.shortkki.api.group.controller;

import com.shortkki.api.group.dto.request.CreateGroupRequest;
import com.shortkki.api.group.dto.request.JoinGroupRequest;
import com.shortkki.api.group.dto.request.UpdateGroupRequest;
import com.shortkki.api.group.dto.response.*;
import com.shortkki.api.group.dto.response.GroupListResponse;
import com.shortkki.api.group.dto.response.GroupMemberResponse;
import com.shortkki.api.group.dto.response.GroupResponse;
import com.shortkki.api.group.service.GroupService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createGroup(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody CreateGroupRequest request
    ) {
        groupService.createGroup(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<BaseResponse<Void>> updateGroup(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @Valid @RequestBody UpdateGroupRequest request
    ) {
        groupService.updateGroup(loginMember.getId(), groupId, request);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<BaseResponse<Void>> deleteGroup(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId
    ) {
        groupService.deleteGroup(loginMember.getId(), groupId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<BaseResponse<GroupResponse>> getGroup(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId
    ) {
        GroupResponse response = groupService.getGroup(loginMember.getId(), groupId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/my")
    public ResponseEntity<BaseResponse<List<GroupListResponse>>> getMyGroups(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        List<GroupListResponse> response = groupService.getMyGroups(loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<BaseResponse<List<GroupMemberResponse>>> getGroupMembers(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId
    ) {
        List<GroupMemberResponse> response = groupService.getGroupMembers(loginMember.getId(),
                groupId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/join")
    public ResponseEntity<BaseResponse<GroupResponse>> joinGroup(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody JoinGroupRequest request
    ) {
        GroupResponse response = groupService.joinGroup(loginMember.getId(), request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/invite/{inviteCode}")
    public ResponseEntity<BaseResponse<GroupPreviewResponse>> getGroupPreview(
            @PathVariable String inviteCode
    ) {
        GroupPreviewResponse response = groupService.getGroupPreviewByInviteCode(inviteCode);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/{groupId}/invite-code")
    public ResponseEntity<BaseResponse<InviteCodeResponse>> getInviteCode(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId
    ) {
        InviteCodeResponse response = groupService.getInviteCode(loginMember.getId(), groupId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
