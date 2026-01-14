package com.example.short_kki.domain.recipe.controller;

import com.example.short_kki.domain.recipe.dto.RecipeCreateRequest;
import com.example.short_kki.domain.recipe.dto.RecipeResponse;
import com.example.short_kki.domain.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * 레시피 생성 POST /api/recipe
     */
    @PostMapping
    public ResponseEntity<RecipeResponse> create(@Valid @RequestBody RecipeCreateRequest request) {
        RecipeResponse response = recipeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 레시피 단건 조회 GET /api/recipe/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> findById(@PathVariable Long id) {
        RecipeResponse response = recipeService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 레시피 전체 조회 GET /api/recipe
     */
    @GetMapping
    public ResponseEntity<List<RecipeResponse>> findAll() {
        List<RecipeResponse> responses = recipeService.findAll();
        return ResponseEntity.ok(responses);
    }

    /**
     * 레시피 삭제 DELETE /api/recipe/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
