package com.shortkki.api.search.infra.elasticsearch.adapter;

import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.application.service.FileUrlResolver;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.Tag;
import com.shortkki.api.recipe.service.RecipeIngredientQueryService;
import com.shortkki.api.recipe.service.RecipeQueryService;
import com.shortkki.api.recipe.service.RecipeTagQueryService;
import com.shortkki.api.search.application.port.RecipeSearchIndexer;
import com.shortkki.api.search.infra.elasticsearch.document.RecipeDocument;
import com.shortkki.api.search.infra.elasticsearch.repository.RecipeDocumentRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElasticsearchRecipeSearchIndexer implements RecipeSearchIndexer {

    private final RecipeQueryService recipeQueryService;
    private final RecipeIngredientQueryService ingredientQueryService;
    private final RecipeTagQueryService tagQueryService;
    private final FileMetadataQueryService fileMetadataQueryService;
    private final FileUrlResolver fileUrlResolver;

    private final RecipeDocumentRepository recipeDocumentRepository;

    @Override
    public void upsert(long recipeId) {
        Recipe recipe = recipeQueryService.findById(recipeId);
        RecipeDocument doc = toDocument(recipe);

        recipeDocumentRepository.save(doc);
    }

    private RecipeDocument toDocument(Recipe recipe) {
        Set<String> ingredients = ingredientQueryService.findIngredientsByRecipeId(recipe.getId()).stream()
                .map(Ingredient::getName)
                .collect(Collectors.toSet());
        Set<String> tags = tagQueryService.findTagsByRecipeId(recipe.getId()).stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
        FileMetadata mainImgFile = fileMetadataQueryService.findById(recipe.getId());
        String mainImgUrl = fileUrlResolver.getUrl(mainImgFile);

        return RecipeDocument.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .sourceType(recipe.getSourceType().name())
                .cuisineType(recipe.getCuisineType().name())
                .mealType(recipe.getMealType().name())
                .difficulty(recipe.getDifficulty().name())
                .ingredients(ingredients)
                .tags(tags)
                .mainImgUrl(mainImgUrl)
                .bookmarkCount(recipe.getBookmarkCount())
                .isActive(recipe.getIsActive())
                .createdAt(recipe.getCreatedAt())
                .build();
    }
}
