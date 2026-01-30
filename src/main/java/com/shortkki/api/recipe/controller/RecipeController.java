package com.shortkki.api.recipe.controller;

import com.shortkki.api.recipe.dto.RecipeCreateRequest;
import com.shortkki.api.recipe.dto.RecipeResponse;
import com.shortkki.api.recipe.dto.RecipeUpdateRequest;
import com.shortkki.api.recipe.service.RecipeQueryService;
import com.shortkki.api.recipe.service.RecipeService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeQueryService recipeQueryService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody RecipeCreateRequest request) {
        RecipeResponse response = recipeService.create(loginMember.getId(), request);
        return ResponseEntity.ok(BaseResponse.success("레시피가 생성되었습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<RecipeResponse>> findById(@PathVariable Long id) {
        RecipeResponse response = recipeQueryService.findById(id);
        return ResponseEntity.ok(BaseResponse.success("레시피가 조회되었습니다.", response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<RecipeResponse>>> findAll() {
        // TODO : 페이지네이션
        List<RecipeResponse> responses = recipeQueryService.findAll();
        return ResponseEntity.ok(BaseResponse.success("레시피 목록이 조회되었습니다.", responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.ok(BaseResponse.success("레시피가 삭제되었습니다."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> update(@PathVariable Long id,
            @Valid @RequestBody RecipeUpdateRequest request) {
        recipeService.update(id, request);
        return ResponseEntity.ok(BaseResponse.success("레시피가 수정되었습니다."));
    }
    // TODO : 외부 레시피 파싱
    // TODO : 태그, 이미 추가
    // TODO : 외부 레시피 생성 로직 분리
}
