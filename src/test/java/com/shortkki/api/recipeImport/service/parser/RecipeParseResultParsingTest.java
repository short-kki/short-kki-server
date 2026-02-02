package com.shortkki.api.recipeImport.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    @DisplayName("RecipeParseResult를 직접 파싱할 수 있는지 테스트 (rawResponse 필드 무시)")
    void testDirectParsing() throws Exception {
        // AI가 반환하는 JSON (rawResponse 필드 없음)
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
                        {"name": "김치", "amount": "200g"},
                        {"name": "돼지고기", "amount": "150g"}
                    ],
                    "steps": [
                        {"stepNumber": 1, "description": "김치를 썬다"},
                        {"stepNumber": 2, "description": "고기를 볶는다"}
                    ]
                }
                """;

        // RecipeParseResult를 직접 파싱 시도
        RecipeParseResult result = objectMapper.readValue(aiJsonResponse, RecipeParseResult.class);

        // 검증
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("김치찌개");
        assertThat(result.description()).isEqualTo("맛있는 김치찌개");
        assertThat(result.servingSize()).isEqualTo(2);
        assertThat(result.cookingTime()).isEqualTo(30);
        assertThat(result.cuisineType()).isEqualTo("KOREAN");
        assertThat(result.mealType()).isEqualTo("MAIN");
        assertThat(result.difficulty()).isEqualTo("BEGINNER");
        assertThat(result.ingredients()).hasSize(2);
        assertThat(result.steps()).hasSize(2);

        // rawResponse는 JSON에 없으므로 null이어야 함
        assertThat(result.rawResponse()).isNull();

        System.out.println("✅ RecipeParseResult 직접 파싱 성공!");
        System.out.println("rawResponse: " + result.rawResponse());
    }

    @Test
    @DisplayName("중간 DTO를 사용한 파싱 테스트")
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
                        {"name": "김치", "amount": "200g"}
                    ],
                    "steps": [
                        {"stepNumber": 1, "description": "김치를 썬다"}
                    ]
                }
                """;

        // 중간 DTO로 파싱
        RecipeParseResultDto dto = objectMapper.readValue(aiJsonResponse, RecipeParseResultDto.class);

        // rawResponse를 수동으로 추가하여 RecipeParseResult 생성
        String rawResponse = aiJsonResponse;
        RecipeParseResult result = new RecipeParseResult(
                dto.title(),
                dto.description(),
                dto.servingSize(),
                dto.cookingTime(),
                dto.cuisineType(),
                dto.mealType(),
                dto.difficulty(),
                dto.ingredients(),
                dto.steps(),
                rawResponse
        );

        // 검증
        assertThat(result.rawResponse()).isNotNull();
        assertThat(result.rawResponse()).contains("김치찌개");

        System.out.println("✅ 중간 DTO 파싱 성공!");
        System.out.println("rawResponse가 정상적으로 설정됨: " + (result.rawResponse() != null));
    }

    // AI 응답 JSON 구조와 일치하는 중간 record
    private record RecipeParseResultDto(
            String title,
            String description,
            Integer servingSize,
            Integer cookingTime,
            String cuisineType,
            String mealType,
            String difficulty,
            List<IngredientParseResult> ingredients,
            List<StepParseResult> steps
    ) {}
}
