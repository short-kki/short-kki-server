package com.shortkki.api.search.infra.elasticsearch.adapter;

import static org.springframework.util.CollectionUtils.isEmpty;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FieldValueFactorModifier;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionBoostMode;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.search.infra.elasticsearch.document.RecipeDocument;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("esRecipeSearch")
@RequiredArgsConstructor
@Slf4j
public class ESRecipeSearchAdapter implements RecipeSearchPort {

    private final ElasticsearchOperations elasticsearchOperations;
    private final RecipeRepository recipeRepository;

    @Override
    public Slice<RecipeSearchItem> search(
            Pageable pageable, String searchWord, Set<String> tags, Set<String> ingredients, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        boolean hasSearchWord = searchWord != null && !searchWord.isBlank();
        BoolQuery.Builder bool = buildBaseBoolQuery(
                searchWord, tags, ingredients, recipeSource, cuisineTypes, mealTypes, difficulties
        );

        NativeQueryBuilder queryBuilder = NativeQuery.builder()
                .withQuery(applyFunctionScore(bool.build()._toQuery()))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .withMaxResults(pageable.getPageSize() + 1)
                .withPageable(pageable);

        if (!hasSearchWord) {
            queryBuilder
                    .withSort(s -> s.field(f -> f.field("bookmarkCount").order(SortOrder.Desc)))
                    .withSort(s -> s.field(f -> f.field("createdAt").order(SortOrder.Desc)));
        }

        return executeSearch(pageable, queryBuilder.build());
    }

    @Override
    public Slice<RecipeSearchItem> searchForCuration(
            Pageable pageable, String searchWord, Set<String> tags, Set<String> ingredients, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        BoolQuery.Builder bool = buildBaseBoolQuery(
                searchWord, tags, ingredients, recipeSource, cuisineTypes, mealTypes, difficulties);

        NativeQuery query = NativeQuery.builder()
                .withQuery(applyCurationFunctionScore(bool.build()._toQuery()))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .withMaxResults(pageable.getPageSize() + 1)
                .withPageable(pageable)
                .build();

        return executeSearch(pageable, query);
    }

    private Slice<RecipeSearchItem> executeSearch(Pageable pageable, NativeQuery query) {
        int pageSize = pageable.getPageSize();

        List<Long> orderedIds = elasticsearchOperations
                .search(query, RecipeDocument.class)
                .getSearchHits().stream()
                .map(hit -> hit.getContent().getId())
                .toList();

        // 다음 페이지 존재 확인
        boolean hasNext = orderedIds.size() > pageSize;
        List<Long> pageIds = hasNext ? orderedIds.subList(0, pageSize) : orderedIds;

        // 레시피 정보 조회
        List<RecipeSearchItem> content = fetchAndOrder(pageIds);
        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BoolQuery.Builder buildBaseBoolQuery(
            String searchWord, Set<String> tags, Set<String> ingredients, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        BoolQuery.Builder bool = new BoolQuery.Builder();
        bool.filter(f -> f.term(t -> t.field("isActive").value(true)));

        boolean hasSearchWord = searchWord != null && !searchWord.isBlank();
        if (hasSearchWord) {
            bool.must(q -> q.multiMatch(mm -> mm
                    .query(searchWord)
                    .type(TextQueryType.BestFields)
                    .operator(Operator.Or)
                    .minimumShouldMatch("1")
                    .fields("title^3", "description", "ingredients^2", "tags^2")
            ));
            bool.should(s -> s.matchPhrase(mp -> mp
                    .field("title")
                    .query(searchWord)
                    .boost(10.0f)
            ));
        }

        addShouldMatch(bool, "tags", tags, 2.0f);
        addShouldMatch(bool, "ingredients", ingredients, 2.0f);

        addEnumFilter(bool, "sourceType", recipeSource);
        addEnumFilter(bool, "cuisineType", cuisineTypes);
        addEnumFilter(bool, "mealType", mealTypes);
        addEnumFilter(bool, "difficulty", difficulties);

        return bool;
    }

    private void addShouldMatch(BoolQuery.Builder bool, String field, Set<String> values, float boost) {
        if (isEmpty(values)) {
            return;
        }
        for (String value : values) {
            bool.should(s -> s.match(m -> m.field(field).query(value).boost(boost)));
        }
    }

    private void addEnumFilter(BoolQuery.Builder bool, String field, Enum<?> value) {
        if (value == null) {
            return;
        }
        bool.filter(f -> f.term(t -> t.field(field).value(value.name())));
    }

    private <E extends Enum<E>> void addEnumFilter(BoolQuery.Builder bool, String field, Set<E> values) {
        if (isEmpty(values)) {
            return;
        }
        List<FieldValue> fieldValues = values.stream()
                .map(e -> FieldValue.of(e.name()))
                .toList();
        bool.filter(f -> f.terms(t -> t.field(field).terms(tv -> tv.value(fieldValues))));
    }

    private Query applyFunctionScore(Query baseQuery) {
        return Query.of(q -> q.functionScore(fs -> fs
                .query(baseQuery)
                .scoreMode(FunctionScoreMode.Sum)
                .boostMode(FunctionBoostMode.Sum)
                .functions(fn -> fn
                        .weight(2.0)
                        .fieldValueFactor(fvf -> fvf
                                .field("bookmarkCount")
                                .factor(1.0)
                                .modifier(FieldValueFactorModifier.Log1p)
                                .missing(0.0)
                        ))
                .functions(fn -> fn
                        .weight(1.0)
                        .gauss(g -> g
                                .date(d -> d
                                        .field("createdAt")
                                        .placement(p -> p
                                                .origin("now")
                                                .scale(Time.of(t -> t.time("21d")))
                                                .offset(Time.of(t -> t.time("7d")))
                                                .decay(0.7)
                                        )
                                )
                        ))
        ));
    }

    private Query applyCurationFunctionScore(Query baseQuery) {
        long todaySeed = LocalDate.now().toEpochDay();

        return Query.of(q -> q.functionScore(fs -> fs
                .query(baseQuery)
                .scoreMode(FunctionScoreMode.Sum)
                .boostMode(FunctionBoostMode.Sum)
                .functions(fn -> fn
                        .weight(0.5)
                        .fieldValueFactor(fvf -> fvf
                                .field("bookmarkCount")
                                .factor(1.0)
                                .modifier(FieldValueFactorModifier.Log1p)
                                .missing(0.0)
                        ))
                .functions(fn -> fn
                        .weight(3.0)
                        .gauss(g -> g
                                .date(d -> d
                                        .field("createdAt")
                                        .placement(p -> p
                                                .origin("now")
                                                .scale(Time.of(t -> t.time("14d")))
                                                .offset(Time.of(t -> t.time("3d")))
                                                .decay(0.5)
                                        )
                                )
                        ))
                .functions(fn -> fn
                        .weight(0.5)
                        .randomScore(rs -> rs
                                .seed(String.valueOf(todaySeed))
                                .field("_seq_no")
                        ))
        ));
    }

    private List<RecipeSearchItem> fetchAndOrder(List<Long> orderedIds) {
        if (orderedIds.isEmpty()) {
            return List.of();
        }

        Map<Long, Recipe> recipeMap = recipeRepository.findActiveByIdsWithAssociations(orderedIds)
                .stream()
                .collect(Collectors.toMap(Recipe::getId, Function.identity()));

        return orderedIds.stream()
                .map(recipeMap::get)
                .filter(Objects::nonNull)
                .map(this::toSearchItem)
                .toList();
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
