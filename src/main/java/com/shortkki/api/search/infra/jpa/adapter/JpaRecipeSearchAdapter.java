package com.shortkki.api.search.infra.jpa.adapter;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.ingredient.entity.QIngredient;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.QRecipe;
import com.shortkki.api.recipe.entity.QRecipeIngredient;
import com.shortkki.api.recipe.entity.QRecipeTag;
import com.shortkki.api.recipe.entity.QTag;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.search.util.SearchWordTokenizer;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("jpaRecipeSearch")
@RequiredArgsConstructor
public class JpaRecipeSearchAdapter implements RecipeSearchPort {

    private final JPAQueryFactory query;

    private final QRecipe recipe = QRecipe.recipe;
    private final QRecipeIngredient recipeIngredient = QRecipeIngredient.recipeIngredient;
    private final QIngredient ingredient = QIngredient.ingredient;
    private final QRecipeTag recipeTag = QRecipeTag.recipeTag;
    private final QTag tag = QTag.tag;

    @Override
    public Slice<RecipeSearchItem> search(
            Pageable pageable, String searchWord, Set<String> tags, Set<String> ingredients, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        Set<String> keywords = SearchWordTokenizer.tokenize(searchWord);
        int pageSize = pageable.getPageSize();

        List<Recipe> results = query
                .selectDistinct(recipe)
                .from(recipe)
                .leftJoin(recipeIngredient).on(recipeIngredient.recipe.id.eq(recipe.id))
                .leftJoin(ingredient).on(ingredient.id.eq(recipeIngredient.ingredient.id))
                .leftJoin(recipeTag).on(recipeTag.recipeId.eq(recipe.id))
                .leftJoin(tag).on(tag.id.eq(recipeTag.tagId))
                .where(buildWhere(keywords, tags, ingredients, recipeSource, cuisineTypes, mealTypes, difficulties))
                .orderBy(recipe.bookmarkCount.desc(), recipe.createdAt.desc(), recipe.id.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1L)
                .fetch();

        boolean hasNext = results.size() > pageSize;
        if (hasNext) {
            results = results.subList(0, pageSize);
        }

        List<RecipeSearchItem> items = results.stream()
                .map(this::toSearchItem)
                .toList();

        return new SliceImpl<>(items, pageable, hasNext);
    }

    private BooleanBuilder buildWhere(
            Set<String> keywords,
            Set<String> tags,
            Set<String> ingredients,
            RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes,
            Set<MealType> mealTypes,
            Set<Difficulty> difficulties
    ) {
        BooleanBuilder where = new BooleanBuilder(recipe.isActive.isTrue());

        // 카테고리 필터
        if (!isEmpty(cuisineTypes)) {
            where.and(recipe.categoryInfo.cuisineType.in(cuisineTypes));
        }
        if (!isEmpty(mealTypes)) {
            where.and(recipe.categoryInfo.mealType.in(mealTypes));
        }
        if (!isEmpty(difficulties)) {
            where.and(recipe.categoryInfo.difficulty.in(difficulties));
        }
        if (recipeSource != null) {
            where.and(recipe.sourceType.eq(recipeSource));
        }

        BooleanBuilder keyword = keywordPredicate(keywords);
        if (keyword != null) {
            where.and(keyword);
        }

        return where;
    }

    private BooleanBuilder keywordPredicate(Set<String> keywords) {
        if (isEmpty(keywords)) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();

        for (String keyword : keywords) {
            if (keyword == null) {
                continue;
            }

            String trimmed = keyword.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            builder.or(matchAnyField(trimmed));
        }

        return builder.hasValue() ? builder : null;
    }

    private BooleanExpression matchAnyField(String keyword) {
        return recipe.basicInfo.title.containsIgnoreCase(keyword)
                .or(recipe.basicInfo.description.containsIgnoreCase(keyword))
                .or(ingredient.name.containsIgnoreCase(keyword))
                .or(tag.name.containsIgnoreCase(keyword));
    }

    private RecipeSearchItem toSearchItem(Recipe recipe) {
        return new RecipeSearchItem(
                recipe.getId(),
                recipe.getBasicInfo().getTitle(),
                recipe.getBookmarkCount(),
                recipe.getMainImgUrl(),
                recipe.getSourceType(),
                recipe.getAuthorName(),
                recipe.getAuthorProfileImgUrl(),
                recipe.getSourcePlatform(),
                recipe.getSourceUrl(),
                recipe.getCreatorName(),
                recipe.getCreatorProfileImgUrl()
        );
    }
}
