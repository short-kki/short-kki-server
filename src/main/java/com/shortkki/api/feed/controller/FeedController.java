package com.shortkki.api.feed.controller;

import com.shortkki.api.feed.dto.request.CreateFeedRequest;
import com.shortkki.api.feed.dto.request.UpdateFeedRequest;
import com.shortkki.api.feed.dto.response.FeedResponse;
import com.shortkki.api.feed.dto.response.FeedSliceResponse;
import com.shortkki.api.feed.service.FeedService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/feeds")
@RequiredArgsConstructor
@Validated
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<BaseResponse<FeedSliceResponse>> getGroupFeeds(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @RequestParam(required = false) @Positive Long cursor,
            @RequestParam(defaultValue = "20") @Positive int size
    ) {
        FeedSliceResponse response = feedService.getGroupFeeds(loginMember.getId(), groupId, cursor, size);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<BaseResponse<FeedResponse>> getFeed(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long feedId
    ) {
        FeedResponse response = feedService.getFeed(loginMember.getId(), groupId, feedId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PatchMapping("/{feedId}")
    public ResponseEntity<BaseResponse<Void>> updateFeed(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long feedId,
            @Valid @RequestBody UpdateFeedRequest request
    ) {
        feedService.updateFeed(loginMember.getId(), groupId, feedId, request);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createFeed(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @Valid @RequestBody CreateFeedRequest request
    ) {
        feedService.createFeed(loginMember.getId(), groupId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<BaseResponse<Void>> deleteFeed(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long feedId
    ) {
        feedService.deleteFeed(loginMember.getId(), groupId, feedId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @PostMapping("/{feedId}/like")
    public ResponseEntity<BaseResponse<Void>> likeFeed(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long feedId
    ) {
        feedService.likeFeed(loginMember.getId(), groupId, feedId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{feedId}/like")
    public ResponseEntity<BaseResponse<Void>> unlikeFeed(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long feedId
    ) {
        feedService.unlikeFeed(loginMember.getId(), groupId, feedId);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
