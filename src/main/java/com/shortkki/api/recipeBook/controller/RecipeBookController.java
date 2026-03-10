package com.shortkki.api.recipeBook.controller;

import com.shortkki.api.recipeBook.dto.RecipeBookAddRecipeRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookCreateRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookDetailResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookIdsResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookListResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookRecipeSortType;
import com.shortkki.api.recipeBook.dto.RecipeBookReorderRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookUpdateRequest;
import com.shortkki.api.recipeBook.dto.RecipeMoveRequest;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<BaseResponse<RecipeBookResponse>> create(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody RecipeBookCreateRequest request) {
        RecipeBookResponse response = recipeBookService.create(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<RecipeBookListResponse>> findAll(
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault(size = 10) Pageable pageable) {
        Pageable sanitizedPageable = sanitizePageable(pageable);
        RecipeBookListResponse responses = recipeBookService.findAllByMember(
                loginMember.getId(), sanitizedPageable);
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<BaseResponse<RecipeBookListResponse>> findAllByGroup(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PageableDefault(size = 10) Pageable pageable) {
        Pageable sanitizedPageable = sanitizePageable(pageable);
        RecipeBookListResponse responses = recipeBookService.findAllByGroup(
                loginMember.getId(), groupId, sanitizedPageable);
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/recipes/{recipeId}")
    public ResponseEntity<BaseResponse<RecipeBookIdsResponse>> findRecipeBookIdsByRecipe(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long recipeId
    ) {
        RecipeBookIdsResponse response = RecipeBookIdsResponse.from(
                recipeBookService.findOwnedRecipeBookIdsByRecipe(loginMember.getId(), recipeId));
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<RecipeBookDetailResponse>> findById(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id,
            @PageableDefault(size = 12) Pageable pageable,
            @RequestParam(name = "recipeSort", defaultValue = "RECENT") RecipeBookRecipeSortType recipeSort
    ) {
        Pageable sanitizedPageable = sanitizePageable(pageable);
        RecipeBookDetailResponse response = recipeBookService.findById(
                loginMember.getId(), id, sanitizedPageable, recipeSort);
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

    @PatchMapping("/{fromBookId}/recipes/{recipeId}")
    public ResponseEntity<BaseResponse<Void>> moveRecipe(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long fromBookId,
            @PathVariable Long recipeId,
            @Valid @RequestBody RecipeMoveRequest request) {
        recipeBookService.moveRecipe(
                loginMember.getId(),
                fromBookId,
                request.toRecipeBookId(),
                recipeId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    private Pageable sanitizePageable(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }
}
