package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.constant.CuisineType;
import com.example.short_kki.domain.recipe.constant.Difficulty;
import com.example.short_kki.domain.recipe.constant.MealType;
import com.example.short_kki.domain.recipe.constant.SourceContentType;
import com.example.short_kki.domain.recipe.constant.SourcePlatform;
import com.example.short_kki.domain.recipe.constant.SourceType;
import com.example.short_kki.domain.recipe.entity.Recipe;
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
            // 링크 가져오기
            return Recipe.createFromLink(
                    basicInfo.title(),
                    basicInfo.description(),
                    basicInfo.servingSize(),
                    basicInfo.cookingTime(),
                    categoryInfo.cuisineType(),
                    categoryInfo.mealType(),
                    categoryInfo.difficulty(),
                    sourceInfo.sourceUrl(),
                    sourceInfo.sourcePlatform(),
                    sourceInfo.sourceContentType());
        }
    }

    // -- 기본 정보 --
    public record BasicInfo(
            @NotBlank(message = "제목은 필수입니다") @Size(max = 100, message = "제목은 100자 이하여야 합니다") String title,

            @Size(max = 500, message = "설명은 500자 이하여야 합니다") String description,

            @NotNull(message = "1인분 기준 양은 필수입니다") @Positive(message = "1인분 기준 양은 양수여야 합니다") Integer servingSize,

            @NotNull(message = "조리 시간은 필수입니다") @Positive(message = "조리 시간은 양수여야 합니다") Integer cookingTime) {

    }

    // -- 카테고리 정보 --
    public record CategoryInfo(
            @NotNull(message = "요리 종류는 필수입니다") CuisineType cuisineType,

            @NotNull(message = "식사 유형은 필수입니다") MealType mealType,

            @NotNull(message = "난이도는 필수입니다") Difficulty difficulty) {

    }

    // -- 출처 정보 (링크 가져오기 시에만 사용) --
    public record SourceInfo(
            @NotBlank(message = "출처 URL은 필수입니다") String sourceUrl,

            @NotNull(message = "출처 플랫폼은 필수입니다") SourcePlatform sourcePlatform,

            @NotNull(message = "출처 컨텐츠 유형은 필수입니다") SourceContentType sourceContentType) {

    }

    // -- 재료 정보 --
    public record IngredientInfo(
            @NotBlank(message = "재료명은 필수입니다") String name,
            String amount
    ) {

    }

    // -- 조리 단계 --
    public record StepInfo(
            @NotNull(message = "순서 번호는 필수입니다") @Positive Integer stepOrder,

            @NotBlank(message = "단계 설명은 필수입니다") String description

    ) {

    }
}
