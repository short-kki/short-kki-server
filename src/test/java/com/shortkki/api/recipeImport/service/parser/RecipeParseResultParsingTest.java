package com.shortkki.api.recipeImport.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.IngredientParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.StepParseResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeParseResultParsingTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("중간 DTO(String) 파싱 후 RecipeParseResult(enum)로 변환 테스트 (tags 포함)")
    void testIntermediateDtoParsing() throws Exception {
        String aiJsonResponse = """
                {
                    "title": "김치찌개",
                    "description": "맛있는 김치찌개",
                    "servingSize": 2,
                    "cookingTime": 30,
                    "cuisineType": "KOREAN",
                    "mealType": "MAIN",
                    "difficulty": "BEGINNER",
                    "ingredients": [
                        {"name": "김치", "amount": 200, "unit": "g"}
                    ],
                    "steps": [
                        {"stepNumber": 1, "description": "김치를 썬다"}
                    ],
                    "tags": ["김치찌개", "한식", "찌개"]
                }
                """;

        RecipeParseResultDto dto = objectMapper.readValue(aiJsonResponse,
                RecipeParseResultDto.class);

        String rawResponse = aiJsonResponse;

        RecipeParseResult result = new RecipeParseResult(
                dto.title(),
                dto.description(),
                dto.servingSize(),
                dto.cookingTime(),
                toCuisineType(dto.cuisineType()),
                toMealType(dto.mealType()),
                toDifficulty(dto.difficulty()),
                dto.ingredients(),
                dto.steps(),
                dto.tags() != null ? dto.tags() : List.of(),
                rawResponse
        );

        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("김치찌개");
        assertThat(result.cuisineType()).isEqualTo(CuisineType.KOREAN);
        assertThat(result.mealType()).isEqualTo(MealType.MAIN);
        assertThat(result.difficulty()).isEqualTo(Difficulty.BEGINNER);
        assertThat(result.ingredients()).hasSize(1);
        assertThat(result.ingredients().get(0).amount()).isEqualTo(200.0);
        assertThat(result.tags()).contains("김치찌개", "한식", "찌개");
        assertThat(result.rawResponse()).isNotNull();

        System.out.println("✅ 중간 DTO 파싱 성공!");
    }

    private CuisineType toCuisineType(String value) {
        try {
            return value == null ? CuisineType.ETC
                    : CuisineType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return CuisineType.ETC;
        }
    }

    private MealType toMealType(String value) {
        try {
            return value == null ? MealType.ETC : MealType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return MealType.ETC;
        }
    }

    private Difficulty toDifficulty(String value) {
        try {
            return value == null ? Difficulty.ETC : Difficulty.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Difficulty.ETC;
        }
    }

    // AI 응답 JSON 구조와 일치하는 중간 record (문자열 enum + amount Double)
    private record RecipeParseResultDto(
            String title,
            String description,
            Integer servingSize,
            Integer cookingTime,
            String cuisineType,
            String mealType,
            String difficulty,
            List<IngredientParseResult> ingredients,
            List<StepParseResult> steps,
            List<String> tags
    ) {

    }
}
