package com.shortkki.api.recipeImport.service.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GoogleSearch;
import com.google.genai.types.Part;
import com.google.genai.types.Tool;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.IngredientParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.StepParseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AiRecipeParserService {

    private static final String MODEL_NAME = "gemini-3.0-flash";
    private final Client client;
    private final ObjectMapper objectMapper;

    public AiRecipeParserService(
            @Value("${GOOGLE_GENAI_API_KEY}") String apiKey,
            ObjectMapper objectMapper) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = objectMapper;
    }

    public RecipeParseResult parseRecipeFromUrl(String youtubeUrl) {
        String prompt = buildPrompt(youtubeUrl);

        try {
            GenerateContentResponse response = client.models.generateContent(
                    MODEL_NAME,
                    Content.builder()
                            .parts(Collections.singletonList(Part.builder().text(prompt).build()))
                            .build(),
                    GenerateContentConfig.builder()
                            .tools(Collections.singletonList(
                                    Tool.builder()
                                            .googleSearch(GoogleSearch.builder().build())
                                            .build()))
                            .responseMimeType("application/json")
                            .build());

            String jsonResult = response.text();
            log.info("AI 파싱 결과: {}", jsonResult);

            return parseJsonResult(jsonResult);

        } catch (Exception e) {
            log.error("AI 레시피 파싱 실패: {}", e.getMessage(), e);
            return RecipeParseResult.empty("파싱 실패");
        }
    }

    private String buildPrompt(String youtubeUrl) {
        return String.format("""
                다음 유튜브 링크의 요리 영상을 분석해서 레시피를 JSON 형식으로 추출해줘.
                
                반드시 다음 형식을 지켜줘:
                {
                    "title": "요리 이름",
                    "description": "요리 설명 (1-2문장)",
                    "servingSize": 2,
                    "cookingTime": 30,
                    "cuisineType": "KOREAN",
                    "mealType": "MAIN",
                    "difficulty": "BEGINNER",
                    "ingredients": [
                        {"name": "재료명", "amount": "용량 (예: 200g, 2큰술)"}
                    ],
                    "steps": [
                        {"stepNumber": 1, "description": "조리 과정 설명"}
                    ]
                }
                
                - servingSize는 인분 수 (숫자만)
                - cookingTime은 분 단위 (숫자만)
                - cuisineType: KOREAN, WESTERN, JAPANESE, CHINESE, ASIAN, FUSION 중 하나
                - mealType: MAIN, SIDE_DISH, SNACK, DESSERT, SIDE_FOR_DRINK 중 하나
                - difficulty: BEGINNER, INTERMEDIATE, ADVANCED 중 하나
                - ingredients와 steps는 빠짐없이 추출
                
                링크: %s
                """, youtubeUrl);
    }

    private RecipeParseResult parseJsonResult(String jsonResult) {
        try {
            JsonNode root = objectMapper.readTree(jsonResult);

            String title = root.path("title").asText("제목 없음");
            String description = root.path("description").asText(null);
            int servingSize = root.path("servingSize").asInt(1);
            int cookingTime = root.path("cookingTime").asInt(30);
            String cuisineType = root.path("cuisineType").asText(null);
            String mealType = root.path("mealType").asText(null);
            String difficulty = root.path("difficulty").asText(null);

            List<IngredientParseResult> ingredients = new ArrayList<>();
            JsonNode ingredientsNode = root.path("ingredients");
            if (ingredientsNode.isArray()) {
                for (JsonNode ing : ingredientsNode) {
                    ingredients.add(new IngredientParseResult(
                            ing.path("name").asText(),
                            ing.path("amount").asText()));
                }
            }

            List<StepParseResult> steps = new ArrayList<>();
            JsonNode stepsNode = root.path("steps");
            if (stepsNode.isArray()) {
                for (JsonNode step : stepsNode) {
                    steps.add(new StepParseResult(
                            step.path("stepNumber").asInt(),
                            step.path("description").asText()));
                }
            }

            return new RecipeParseResult(
                    title, description, servingSize, cookingTime,
                    cuisineType, mealType, difficulty,
                    ingredients, steps);

        } catch (Exception e) {
            log.error("JSON 파싱 실패: {}", e.getMessage(), e);
            return RecipeParseResult.empty("파싱 실패");
        }
    }
}
