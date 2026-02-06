package com.shortkki.api.recipe.dto.response;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.entity.RecipeStep;
import java.time.LocalDateTime;
import java.util.List;

public record RecipeResponse(
        Long id,
        String title,
        String description,
        Integer servingSize,
        Integer cookingTime,
        Integer bookmarkCount,
        CuisineType cuisineType,
        MealType mealType,
        Difficulty difficulty,
        RecipeSource recipeSource,
        String sourceUrl,
        SourcePlatform sourcePlatform,
        SourceContentType sourceContentType,
        RecipeAuthorResponse author,
        RecipeCreatorResponse creator,
        List<StepResponse> steps,
        List<IngredientResponse> ingredients,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static RecipeResponse toDto(
            Recipe recipe, List<RecipeStep> steps, List<RecipeIngredient> ingredients, List<String> tags
    ) {
        List<StepResponse> stepResponses = steps.stream()
                .map(s -> new StepResponse(s.getStepOrder(), s.getDescription()))
                .toList();

        List<IngredientResponse> ingredientResponses = ingredients.stream()
                .map(i -> new IngredientResponse(
                        i.getName(),
                        i.getUnit(),
                        i.getAmount())
                )
                .toList();

        return new RecipeResponse(
                recipe.getId(),
                recipe.getBasicInfo().getTitle(),
                recipe.getBasicInfo().getDescription(),
                recipe.getBasicInfo().getServingSize(),
                recipe.getBasicInfo().getCookingTime(),
                recipe.getBookmarkCount(),
                recipe.getCategoryInfo().getCuisineType(),
                recipe.getCategoryInfo().getMealType(),
                recipe.getCategoryInfo().getDifficulty(),
                recipe.getSourceType(),
                recipe.getSourceUrl(),
                recipe.getSourcePlatform(),
                recipe.getSourceContentType(),
                new RecipeAuthorResponse(recipe.getAuthorName(), recipe.getAuthorProfileImgUrl()),
                new RecipeCreatorResponse(recipe.getSourcePlatform(), recipe.getCreatorName(), recipe.getCreatorProfileImgUrl()),
                stepResponses,
                ingredientResponses,
                tags != null ? tags : List.of(),
                recipe.getCreatedAt(),
                recipe.getUpdatedAt()
        );
    }
}
