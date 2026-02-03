package com.shortkki.api.calendar.controller;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeQueueRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeQueueDetailResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeQueuesResponse;
import com.shortkki.api.calendar.service.RecipeQueueQueryService;
import com.shortkki.api.calendar.service.RecipeQueueService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calendar/queue")
@RequiredArgsConstructor
public class RecipeQueueController {

    private final RecipeQueueService recipeQueueService;
    private final RecipeQueueQueryService recipeQueueQueryService;

    @Operation(summary = "레시피 대기열 목록 조회")
    @GetMapping
    public ResponseEntity<BaseResponse<RecipeQueuesResponse>> getList(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        RecipeQueuesResponse response = recipeQueueQueryService.getRecipeQueues(loginMember.getId());
        return ResponseEntity.ok(BaseResponse.success("레시피 대기열 목록을 조회했습니다.", response));
    }

    @Operation(summary = "레시피 대기열 생성")
    @PostMapping
    public ResponseEntity<BaseResponse<RecipeQueueDetailResponse>> create(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody CreateRecipeQueueRequest request
    ) {
        RecipeQueueDetailResponse response = recipeQueueService.create(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("레시피 큐에 추가되었습니다.", response));
    }

    @Operation(summary = "레시피 대기열 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id
    ) {
        recipeQueueService.delete(loginMember.getId(), id);
        return ResponseEntity.ok(BaseResponse.success("레시피 대기열이 삭제되었습니다."));
    }
}
