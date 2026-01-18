package com.example.short_kki.domain.recipe.controller;

import com.example.short_kki.domain.recipe.dto.RecipeCreateRequest;
import com.example.short_kki.domain.recipe.dto.RecipeResponse;
import com.example.short_kki.domain.recipe.service.RecipeService;
import com.example.short_kki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * 레시피 생성 POST /api/recipes
     */
    @PostMapping
    public ResponseEntity<BaseResponse<RecipeResponse>> create(
            @Valid @RequestBody RecipeCreateRequest request) {
        RecipeResponse response = recipeService.create(request);
        return ResponseEntity.ok(BaseResponse.success("레시피가 생성되었습니다.", response));
    }

    /**
     * 레시피 단건 조회 GET /api/recipes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<RecipeResponse>> findById(@PathVariable Long id) {
        RecipeResponse response = recipeService.findById(id);
        return ResponseEntity.ok(BaseResponse.success("레시피가 조회되었습니다.", response));
    }

    /**
     * 레시피 전체 조회 GET /api/recipes
     */
    @GetMapping
    public ResponseEntity<BaseResponse<List<RecipeResponse>>> findAll() {
        // TODO : 페이지네이션
        List<RecipeResponse> responses = recipeService.findAll();
        return ResponseEntity.ok(BaseResponse.success("레시피 목록이 조회되었습니다.", responses));
    }

    /**
     * 레시피 삭제 DELETE /api/recipes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.ok(BaseResponse.success("레시피가 삭제되었습니다."));
    }
}
