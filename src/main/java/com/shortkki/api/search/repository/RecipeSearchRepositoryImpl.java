package com.shortkki.api.search.repository;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.QRecipe;
import com.shortkki.api.recipe.entity.QRecipeIngredient;
import com.shortkki.api.recipe.entity.QRecipeTag;
import com.shortkki.api.recipe.entity.QTag;
import com.shortkki.api.recipe.entity.Recipe;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecipeSearchRepositoryImpl implements RecipeSearchRepository {

    private final JPAQueryFactory query;

    private final QRecipe recipe = QRecipe.recipe;
    private final QRecipeIngredient recipeIngredient = QRecipeIngredient.recipeIngredient;
    private final QRecipeTag recipeTag = QRecipeTag.recipeTag;
    private final QTag tag = QTag.tag;

    @Override
    public Slice<Recipe> search(
            Pageable pageable, Set<String> keywords,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        int pageSize = pageable.getPageSize();

        List<Recipe> results = query
                .selectDistinct(recipe)
                .from(recipe)
                .leftJoin(recipe.ingredients, recipeIngredient)
                .leftJoin(recipeTag).on(recipeTag.recipeId.eq(recipe.id))
                .leftJoin(tag).on(tag.id.eq(recipeTag.tagId))
                .where(where(keywords, cuisineTypes, mealTypes, difficulties))
                .orderBy(recipe.bookmarkCount.desc(), recipe.createdAt.desc(), recipe.id.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1L)
                .fetch();

        boolean hasNext = results.size() > pageSize;
        if (hasNext) {
            results = results.subList(0, pageSize);
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    private BooleanBuilder where(
            Set<String> keywords,
            Set<CuisineType> cuisineTypes,
            Set<MealType> mealTypes,
            Set<Difficulty> difficulties
    ) {
        BooleanBuilder where = new BooleanBuilder(recipe.isDeleted.isFalse());

        if (!isEmpty(cuisineTypes)) {
            where.and(recipe.cuisineType.in(cuisineTypes));
        }
        if (!isEmpty(mealTypes)) {
            where.and(recipe.mealType.in(mealTypes));
        }

        if (!isEmpty(difficulties)) {
            where.and(recipe.difficulty.in(difficulties));
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

        BooleanBuilder andKeywords = new BooleanBuilder();

        for (String keyword : keywords) {
            if (keyword == null) {
                continue;
            }

            String trimmed = keyword.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            andKeywords.or(matchAnyField(trimmed));
        }

        return andKeywords.hasValue() ? andKeywords : null;
    }

    private BooleanExpression matchAnyField(String keyword) {
        return recipe.title.containsIgnoreCase(keyword)
                .or(recipe.description.containsIgnoreCase(keyword))
                .or(recipeIngredient.ingredient.name.containsIgnoreCase(keyword))
                .or(tag.name.containsIgnoreCase(keyword));
    }
}
