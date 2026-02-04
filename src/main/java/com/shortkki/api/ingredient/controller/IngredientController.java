package com.shortkki.api.ingredient.controller;

import com.shortkki.api.ingredient.controller.dto.IngredientsResponse;
import com.shortkki.api.recipe.service.IngredientQueryService;
import com.shortkki.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientQueryService ingredientQueryService;

    @GetMapping
    public ResponseEntity<BaseResponse<IngredientsResponse>> getAll() {
        IngredientsResponse response = ingredientQueryService.getAll();
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
