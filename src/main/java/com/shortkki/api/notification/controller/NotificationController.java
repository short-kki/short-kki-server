package com.shortkki.api.notification.controller;

import com.shortkki.api.notification.dto.response.NotificationResponse;
import com.shortkki.api.notification.dto.response.UnreadCountResponse;
import com.shortkki.api.notification.service.NotificationService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import com.shortkki.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<BaseResponse<SliceResponse<NotificationResponse>>> getNotifications(
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        SliceResponse<NotificationResponse> response = notificationService.getNotifications(
                loginMember.getId(), pageable);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<BaseResponse<UnreadCountResponse>> getUnreadCount(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        UnreadCountResponse response = notificationService.getUnreadCount(loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<BaseResponse<Void>> markAsRead(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long notificationId
    ) {
        notificationService.markAsRead(loginMember.getId(), notificationId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @PatchMapping("/read-all")
    public ResponseEntity<BaseResponse<Void>> markAllAsRead(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        notificationService.markAllAsRead(loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success());
    }
}
