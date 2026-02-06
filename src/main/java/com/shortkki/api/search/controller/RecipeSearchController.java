package com.shortkki.api.search.controller;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.search.application.service.RecipeSearchService;
import com.shortkki.api.search.controller.dto.RecipeSearchResponse;
import com.shortkki.global.response.BaseResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final RecipeSearchService recipeSearchService;

    @GetMapping("/api/v1/recipes/search")
    public ResponseEntity<BaseResponse<RecipeSearchResponse>> search(
            @RequestParam String searchWord,
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) RecipeSource recipeSource,
            @RequestParam(required = false) Set<CuisineType> cuisineTypes,
            @RequestParam(required = false) Set<MealType> mealTypes,
            @RequestParam(required = false) Set<Difficulty> difficulties
    ) {
        RecipeSearchResponse response = recipeSearchService.search(
                pageable, searchWord, recipeSource, cuisineTypes, mealTypes, difficulties
        );
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/api/v2/recipes/search")
    public ResponseEntity<BaseResponse<RecipeSearchResponse>> searchV2(
            @RequestParam(required = false) String searchWord,
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) RecipeSource recipeSource,
            @RequestParam(required = false) Set<CuisineType> cuisineTypes,
            @RequestParam(required = false) Set<MealType> mealTypes,
            @RequestParam(required = false) Set<Difficulty> difficulties
    ) {
        RecipeSearchResponse response = recipeSearchService.searchV2(
                pageable, searchWord, recipeSource, cuisineTypes, mealTypes, difficulties
        );
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
