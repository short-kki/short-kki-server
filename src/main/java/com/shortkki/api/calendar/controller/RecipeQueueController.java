package com.shortkki.api.calendar.controller;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeQueueRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeQueueResponse;
import com.shortkki.api.calendar.service.RecipeQueueService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipes/queue")
@RequiredArgsConstructor
public class RecipeQueueController {

    private final RecipeQueueService recipeQueueService;

    @PostMapping
    public ResponseEntity<BaseResponse<RecipeQueueResponse>> create(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody CreateRecipeQueueRequest request
    ) {
        RecipeQueueResponse response = recipeQueueService.create(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("레시피 큐에 추가되었습니다.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id
    ) {
        recipeQueueService.delete(loginMember.getId(), id);
        return ResponseEntity.ok(BaseResponse.success("레시피 대기열이 삭제되었습니다."));
    }
}
