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
import com.shortkki.api.source.domain.SourceContent;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ESRecipeSearchIndexer implements RecipeSearchIndexer {

    private final RecipeQueryService recipeQueryService;
    private final RecipeIngredientQueryService ingredientQueryService;
    private final RecipeTagQueryService tagQueryService;

    private final RecipeDocumentRepository recipeDocumentRepository;

    @Override
    public void upsert(long recipeId) {
        Recipe recipe = recipeQueryService.findById(recipeId);
        RecipeDocument doc = toDocument(recipe);

        recipeDocumentRepository.save(doc);
    }

    @Override
    public void upsertAll(List<Recipe> recipes) {
        List<RecipeDocument> docs = recipes.stream()
                .map(this::toDocument)
                .toList();
        recipeDocumentRepository.saveAll(docs);
    }

    @Override
    public void delete(long recipeId) {
        recipeDocumentRepository.deleteById(recipeId);
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
        SourceContent sourceContent = recipe.getSourceContent();

        return new RecipeDocument(
                recipe.getId(),
                basicInfo.getTitle(),
                basicInfo.getDescription(),
                recipe.getBookmarkCount(),
                recipe.getSourceType().name(),
                sourceContent != null ? sourceContent.getContentType().name() : null,
                sourceContent != null ? sourceContent.getContentStatus().name() : null,
                sourceContent != null ? sourceContent.isPlayable() : true,
                categoryInfo.getCuisineType().name(),
                categoryInfo.getMealType().name(),
                categoryInfo.getDifficulty().name(),
                ingredients,
                tags,
                recipe.getIsActive(),
                recipe.getCreatedAt().toLocalDate()
        );
    }
}
