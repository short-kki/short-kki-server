package com.shortkki.api.search.infra.elasticsearch.adapter;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.Tag;
import com.shortkki.api.recipe.entity.vo.RecipeBasicInfo;
import com.shortkki.api.recipe.entity.vo.RecipeCategoryInfo;
import com.shortkki.api.recipe.service.RecipeIngredientQueryService;
import com.shortkki.api.recipe.service.RecipeQueryService;
import com.shortkki.api.recipe.service.RecipeTagQueryService;
import com.shortkki.api.search.application.port.RecipeSearchIndexer;
import com.shortkki.api.search.infra.elasticsearch.document.RecipeDocument;
import com.shortkki.api.search.infra.elasticsearch.repository.RecipeDocumentRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.elasticsearch.uris")
public class ESRecipeSearchIndexer implements RecipeSearchIndexer {

    private final RecipeQueryService recipeQueryService;
    private final RecipeIngredientQueryService ingredientQueryService;
    private final RecipeTagQueryService tagQueryService;

    private final RecipeDocumentRepository recipeDocumentRepository;

    @Override
    @Transactional(readOnly = true)
    public void upsert(long recipeId) {
        Recipe recipe = recipeQueryService.findById(recipeId);
        RecipeDocument doc = toDocument(recipe);

        recipeDocumentRepository.save(doc);
    }

    private RecipeDocument toDocument(Recipe recipe) {
        Set<String> ingredients = ingredientQueryService.findIngredientsByRecipeId(recipe.getId())
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toSet());

        Set<String> tags = tagQueryService.findTagsByRecipeId(recipe.getId()).stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        RecipeBasicInfo basicInfo = recipe.getBasicInfo();
        RecipeCategoryInfo categoryInfo = recipe.getCategoryInfo();

        return new RecipeDocument(
                recipe.getId(),
                basicInfo.getTitle(),
                basicInfo.getDescription(),
                recipe.getSourceType().name(),
                recipe.getBookmarkCount(),
                recipe.getMainImgUrl(),
                categoryInfo.getCuisineType().name(),
                categoryInfo.getMealType().name(),
                categoryInfo.getDifficulty().name(),
                ingredients,
                tags,
                recipe.getAuthorName(),
                recipe.getAuthorProfileImgUrl(),
                recipe.getCreatorName(),
                recipe.getCreatorProfileImgUrl(),
                recipe.isImported() ? recipe.getSourcePlatform().name() : null,
                recipe.getSourceUrl(),
                recipe.getIsActive(),
                recipe.getCreatedAt().toLocalDate()
        );
    }
}
