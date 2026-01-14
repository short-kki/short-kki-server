package com.example.short_kki.domain.recipe.dto;

import com.example.short_kki.domain.recipe.constant.CuisineType;
import com.example.short_kki.domain.recipe.constant.Difficulty;
import com.example.short_kki.domain.recipe.constant.MealType;
import com.example.short_kki.domain.recipe.constant.SourceContentType;
import com.example.short_kki.domain.recipe.constant.SourcePlatform;
import com.example.short_kki.domain.recipe.entity.Recipe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

/**
 * 링크 가져오기용 레시피 생성 요청 DTO
 */
public record RecipeImportRequest(
        @Valid @NotNull(message = "기본 정보는 필수입니다") BasicInfo basicInfo,

        @Valid @NotNull(message = "카테고리 정보는 필수입니다") CategoryInfo categoryInfo,

        @Valid @NotNull(message = "출처 정보는 필수입니다") SourceInfo sourceInfo) {


    public Recipe toEntity() {
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

    /**
     * 레시피 기본 정보
     */
    public record BasicInfo(
            @NotBlank(message = "제목은 필수입니다") @Size(max = 100, message = "제목은 100자 이하여야 합니다") String title,

            @Size(max = 500, message = "설명은 500자 이하여야 합니다") String description,

            @NotNull(message = "1인분 기준 양은 필수입니다") @Positive(message = "1인분 기준 양은 양수여야 합니다") Integer servingSize,

            @NotNull(message = "조리 시간은 필수입니다") @Positive(message = "조리 시간은 양수여야 합니다") Integer cookingTime) {

    }

    /**
     * 레시피 카테고리 정보
     */
    public record CategoryInfo(
            @NotNull(message = "요리 종류는 필수입니다") CuisineType cuisineType,

            @NotNull(message = "식사 유형은 필수입니다") MealType mealType,

            @NotNull(message = "난이도는 필수입니다") Difficulty difficulty) {

    }

    /**
     * 레시피 출처 정보
     */
    public record SourceInfo(
            @NotNull(message = "출처 URL은 필수입니다") @URL(message = "올바른 URL 형식이어야 합니다") String sourceUrl,

            @NotNull(message = "출처 플랫폼은 필수입니다") SourcePlatform sourcePlatform,

            @NotNull(message = "출처 컨텐츠 유형은 필수입니다") SourceContentType sourceContentType) {

    }
}
