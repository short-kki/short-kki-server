package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.entity.Recipe;
import com.example.short_kki.domain.recipe.entity.RecipeSource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 레시피 생성 요청 DTO (통합) - 수동 입력 / 링크 가져오기 모두 이 DTO 하나로 처리 태그는 추후
 */
public record RecipeCreateRequest(
        @Valid @NotNull(message = "기본 정보는 필수입니다") BasicInfo basicInfo,

        @Valid @NotNull(message = "카테고리 정보는 필수입니다") CategoryInfo categoryInfo,

        @Valid SourceInfo sourceInfo,

        @Valid List<IngredientInfo> ingredients,

        @Valid List<StepInfo> steps) {

    /**
     * Recipe 엔티티로 변환 sourceInfo 유무에 따라 수동/링크 가져오기 분기
     */
    public Recipe toEntity() {
        if (sourceInfo == null) {
            // 수동 입력
            return Recipe.createManual(
                    basicInfo.title(),
                    basicInfo.description(),
                    basicInfo.servingSize(),
                    basicInfo.cookingTime(),
                    categoryInfo.cuisineType(),
                    categoryInfo.mealType(),
                    categoryInfo.difficulty());
        } else {
            RecipeSource recipeSource = new RecipeSource(
                    sourceInfo.sourceUrl(),
                    sourceInfo.sourcePlatform(),
                    sourceInfo.sourceContentType());

            // 링크 가져오기
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

}
