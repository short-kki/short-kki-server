package com.shortkki.api.recipeImport.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.FileData;
import com.google.genai.types.Part;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.IngredientParseResult;
import com.shortkki.api.recipeImport.dto.RecipeParseResult.StepParseResult;
import com.shortkki.api.recipeImport.port.RecipeParserPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class GeminiRecipeParserAdapter implements RecipeParserPort {

    private static final String PROMPT_TEMPLATE;

    static {
        try {
            ClassPathResource resource = new ClassPathResource("prompts/recipe-parser-prompt.txt");
            PROMPT_TEMPLATE = new String(resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load recipe parser prompt template", e);
        }
    }

    private final String modelName;
    private final Client client;
    private final ObjectMapper objectMapper;

    public GeminiRecipeParserAdapter(
            @Value("${GOOGLE_GENAI_API_KEY}") String apiKey,
            @Value("${gemini.model-name}") String modelName,
            ObjectMapper objectMapper
    ) {
        this.modelName = modelName;
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = objectMapper;
    }

    @Override
    public RecipeParseResult parseRecipeFromUrl(String youtubeUrl) {
        try {
            GenerateContentResponse response = client.models.generateContent(
                    modelName,
                    Content.builder()
                            .parts(Arrays.asList(
                                    Part.builder().text(PROMPT_TEMPLATE).build(),
                                    Part.builder()
                                            .fileData(FileData.builder()
                                                    .fileUri(youtubeUrl)
                                                    .build())
                                            .build()))
                            .build(),
                    GenerateContentConfig.builder()
                            .responseMimeType("application/json")
                            .build());

            String jsonResult = response.text();
            if (jsonResult == null || jsonResult.isBlank()) {
                return RecipeParseResult.empty("파싱 실패");
            }
            String cleanedJson = stripMarkdown(jsonResult);
            return parseJsonResult(cleanedJson, jsonResult);

        } catch (Exception e) {
            log.error("AI 레시피 파싱 실패: {}", e.getMessage(), e);
            return RecipeParseResult.empty("파싱 실패");
        }
    }

    private String stripMarkdown(String content) {
        if (content == null) {
            return "";
        }
        String stripped = content.trim();
        if (stripped.startsWith("```json")) {
            stripped = stripped.substring(7);
        } else if (stripped.startsWith("```")) {
            stripped = stripped.substring(3);
        }
        if (stripped.endsWith("```")) {
            stripped = stripped.substring(0, stripped.length() - 3);
        }
        return stripped.trim();
    }

    private RecipeParseResult parseJsonResult(String jsonResult, String rawResponse) {
        try {
            // RecipeParseResult를 직접 파싱 (rawResponse 제외)
            RecipeParseResultDto dto = objectMapper.readValue(jsonResult,
                    RecipeParseResultDto.class);

            CuisineType cuisineType = parseEnum(dto.cuisineType(), CuisineType.ETC,
                    CuisineType.class);
            MealType mealType = parseEnum(dto.mealType(), MealType.ETC, MealType.class);
            Difficulty difficulty = parseEnum(dto.difficulty(), Difficulty.ETC, Difficulty.class);

            // rawResponse를 포함한 최종 RecipeParseResult 생성
            return new RecipeParseResult(
                    dto.title() != null ? dto.title() : "제목 없음",
                    dto.description(),
                    dto.servingSize() != null ? dto.servingSize() : 1,
                    dto.cookingTime() != null ? dto.cookingTime() : 10,
                    cuisineType,
                    mealType,
                    difficulty,
                    dto.ingredients() != null ? dto.ingredients() : List.of(),
                    dto.steps() != null ? dto.steps() : List.of(),
                    dto.tags() != null ? dto.tags() : List.of(),
                    rawResponse);

        } catch (Exception e) {
            log.error("JSON 파싱 실패", e);
            return RecipeParseResult.empty("파싱 실패");
        }
    }

    private <E extends Enum<E>> E parseEnum(String value, E fallback, Class<E> enumType) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        try {
            return Enum.valueOf(enumType, value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }

    // AI 응답 JSON 구조와 일치하는 중간 record (rawResponse 제외)
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
