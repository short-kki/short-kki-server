package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.entity.Recipe;
import com.example.short_kki.domain.recipe.entity.RecipeSource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RecipeCreateRequest(
                @Valid @NotNull(message = "기본 정보는 필수입니다") BasicInfoRequest basicInfo,

                @Valid @NotNull(message = "카테고리 정보는 필수입니다") CategoryInfoRequest categoryInfo,

                @Valid SourceInfoRequest sourceInfo,

                @Valid @NotNull(message = "재료는 필수입니다.") @Size(min = 1, message = "재료는 최소 1개 이상이어야 합니다.") List<IngredientRequest> ingredients,

                @Valid @NotNull(message = "조리 순서는 필수입니다.") @Size(min = 1, message = "조리 순서는 최소 1개 이상이어야 합니다.") List<StepRequest> steps) {
        public Recipe toEntity() {
                if (sourceInfo == null) {
                        return Recipe.createManual(
                                        basicInfo.title(),
                                        basicInfo.description(),
                                        basicInfo.servingSize(),
                                        basicInfo.cookingTime(),
                                        categoryInfo.cuisineType(),
                                        categoryInfo.mealType(),
                                        categoryInfo.difficulty());
                }

                RecipeSource recipeSource = new RecipeSource(
                                sourceInfo.sourceUrl(),
                                sourceInfo.sourcePlatform(),
                                sourceInfo.sourceContentType());

                return Recipe.createFromLink(
                                basicInfo.title(),
                                basicInfo.description(),
                                basicInfo.servingSize(),
                                basicInfo.cookingTime(),
                                categoryInfo.cuisineType(),
                                categoryInfo.mealType(),
                                categoryInfo.difficulty(),
                                recipeSource);
        }
}
