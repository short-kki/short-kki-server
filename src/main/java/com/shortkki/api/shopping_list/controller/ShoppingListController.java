package com.shortkki.api.shopping_list.controller;

import com.shortkki.api.shopping_list.dto.request.CreateShoppingListRequest;
import com.shortkki.api.shopping_list.dto.request.UpdateShoppingListRequest;
import com.shortkki.api.shopping_list.dto.response.ShoppingListResponse;
import com.shortkki.api.shopping_list.service.ShoppingListService;
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
@RequestMapping("/api/v1/groups/{groupId}/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<ShoppingListResponse>>> getShoppingList(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId
    ) {
        List<ShoppingListResponse> response = shoppingListService.getShoppingList(
                loginMember.getId(), groupId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // TODO : 그룹별 장바구니는 그룹 생성 시점에 같이 만들어줄까?
    @PostMapping
    public ResponseEntity<BaseResponse<ShoppingListResponse>> createShoppingList(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @Valid @RequestBody CreateShoppingListRequest request
    ) {
        ShoppingListResponse response = shoppingListService.createShoppingList(loginMember.getId(),
                groupId, request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @PatchMapping("/{shoppingListId}")
    public ResponseEntity<BaseResponse<Void>> updateShoppingList(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long shoppingListId,
            @Valid @RequestBody UpdateShoppingListRequest request
    ) {
        shoppingListService.updateShoppingList(loginMember.getId(), groupId, shoppingListId,
                request.name());
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{shoppingListId}")
    public ResponseEntity<BaseResponse<Void>> deleteShoppingList(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long shoppingListId
    ) {
        shoppingListService.deleteShoppingList(loginMember.getId(), groupId, shoppingListId);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
