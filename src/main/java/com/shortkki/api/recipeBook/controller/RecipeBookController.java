package com.shortkki.api.recipeBook.controller;

import com.shortkki.api.recipeBook.dto.RecipeBookAddRecipeRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookCreateRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookReorderRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookUpdateRequest;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipebooks")
@RequiredArgsConstructor
public class RecipeBookController {

    private final RecipeBookService recipeBookService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody RecipeBookCreateRequest request
    ) {
        recipeBookService.create(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<RecipeBookResponse>>> findAll(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam(required = false) Long groupId
    ) {
        List<RecipeBookResponse> responses;
        if (groupId != null) {
            responses = recipeBookService.findAllByGroup(loginMember.getId(), groupId);
        } else {
            responses = recipeBookService.findAllByMember(loginMember.getId());
        }

        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<RecipeBookResponse>> findById(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id) {
        RecipeBookResponse response = recipeBookService.findById(loginMember.getId(), id);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updateTitle(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id,
            @Valid @RequestBody RecipeBookUpdateRequest request) {
        recipeBookService.updateTitle(loginMember.getId(), id, request);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @PatchMapping("/order")
    public ResponseEntity<BaseResponse<Void>> reorder(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody RecipeBookReorderRequest request) {
        recipeBookService.reorder(loginMember.getId(), request);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id) {
        recipeBookService.delete(loginMember.getId(), id);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @PostMapping("/{id}/recipes")
    public ResponseEntity<BaseResponse<Void>> addRecipe(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id,
            @Valid @RequestBody RecipeBookAddRecipeRequest request) {
        recipeBookService.addRecipe(loginMember.getId(), id, request.recipeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }

    @DeleteMapping("/{id}/recipes/{recipeId}")
    public ResponseEntity<BaseResponse<Void>> removeRecipe(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id,
            @PathVariable Long recipeId) {
        recipeBookService.removeRecipe(loginMember.getId(), id, recipeId);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
