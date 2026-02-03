package com.shortkki.api.search.infra.jpa.adapter;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.file.application.service.FileUrlResolver;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.QRecipe;
import com.shortkki.api.recipe.entity.QRecipeIngredient;
import com.shortkki.api.recipe.entity.QRecipeTag;
import com.shortkki.api.recipe.entity.QTag;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.search.util.SearchWordTokenizer;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaRecipeSearchAdapter implements RecipeSearchPort {

    private final JPAQueryFactory query;

    private final QRecipe recipe = QRecipe.recipe;
    private final QRecipeIngredient recipeIngredient = QRecipeIngredient.recipeIngredient;
    private final QRecipeTag recipeTag = QRecipeTag.recipeTag;
    private final QTag tag = QTag.tag;

    private final FileUrlResolver fileUrlResolver;

    @Override
    public Slice<RecipeSearchItem> search(
            Pageable pageable, String searchWord,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        Set<String> keywords = SearchWordTokenizer.tokenize(searchWord);
        int pageSize = pageable.getPageSize();

        List<Recipe> results = query
                .selectDistinct(recipe)
                .from(recipe)
                .leftJoin(recipeIngredient).on(recipeIngredient.recipe.eq(recipe))
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

        List<RecipeSearchItem> items = results.stream()
                .map(this::toSearchItem)
                .toList();

        return new SliceImpl<>(items, pageable, hasNext);
    }

    private RecipeSearchItem toSearchItem(Recipe recipe) {
        return new RecipeSearchItem(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getBookmarkCount(),
                fileUrlResolver.getUrl(recipe.getMainImgFile()),
                recipe.getMember().getName()
        );
    }

    private BooleanBuilder where(
            Set<String> keywords,
            Set<CuisineType> cuisineTypes,
            Set<MealType> mealTypes,
            Set<Difficulty> difficulties
    ) {
        BooleanBuilder where = new BooleanBuilder(recipe.isActive.isFalse());

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
