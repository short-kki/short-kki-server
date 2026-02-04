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
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.search.infra.elasticsearch.document.RecipeDocument;
import com.shortkki.api.source.domain.SourcePlatform;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("esRecipeSearch")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.elasticsearch.uris")
@Slf4j
public class ESRecipeSearchAdapter implements RecipeSearchPort {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Slice<RecipeSearchItem> search(
            Pageable pageable, String searchWord, Set<String> tags, Set<String> ingredients, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        int pageSize = pageable.getPageSize();
        boolean hasSearchWord = searchWord != null && !searchWord.isBlank();

        NativeQueryBuilder queryBuilder = NativeQuery.builder()
                .withQuery(buildQuery(searchWord, tags, ingredients, recipeSource, cuisineTypes, mealTypes, difficulties))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .withMaxResults(pageSize + 1)
                .withPageable(pageable);

        if (!hasSearchWord) {
            queryBuilder
                    .withSort(s -> s.field(f -> f.field("bookmarkCount").order(SortOrder.Desc)))
                    .withSort(s -> s.field(f -> f.field("createdAt").order(SortOrder.Desc)));
        }

        List<RecipeSearchItem> items = elasticsearchOperations
                .search(queryBuilder.build(), RecipeDocument.class)
                .getSearchHits().stream()
                .map(hit -> toSearchItem(hit.getContent()))
                .toList();

        boolean hasNext = items.size() > pageSize;
        List<RecipeSearchItem> content = hasNext ? items.subList(0, pageSize) : items;
        return new SliceImpl<>(content, pageable, hasNext);
    }

    private Query buildQuery(
            String searchWord, Set<String> tags, Set<String> ingredients, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        BoolQuery.Builder bool = new BoolQuery.Builder();
        bool.filter(f -> f.term(t -> t.field("isActive").value(true)));

        // 검색어
        boolean hasSearchWord = searchWord != null && !searchWord.isBlank();
        if (hasSearchWord) {
            bool.must(m -> m.multiMatch(mm -> mm
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

        // 태그, 재료
        addShouldMatch(bool, "tags", tags, 2.0f);
        addShouldMatch(bool, "ingredients", ingredients, 2.0f);

        // 카테고리 필터
        addEnumFilter(bool, "sourceType", recipeSource);
        addEnumFilter(bool, "cuisineType", cuisineTypes);
        addEnumFilter(bool, "mealType", mealTypes);
        addEnumFilter(bool, "difficulty", difficulties);

        return applyFunctionScore(bool.build()._toQuery());
    }

    private void addShouldMatch(BoolQuery.Builder bool, String field, Set<String> values, float boost) {
        if (isEmpty(values)) {
            return;
        }
        for (String value : values) {
            bool.should(s -> s.match(m -> m.field(field).query(value).boost(boost)));
        }
    }

    // filter
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

    // score function
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
                                                .scale(Time.of(t -> t.time("28d")))
                                                .offset(Time.of(t -> t.time("7d")))
                                                .decay(0.7)
                                        )
                                )
                        ))
        ));
    }

    private RecipeSearchItem toSearchItem(RecipeDocument doc) {
        return new RecipeSearchItem(
                doc.getId(),
                doc.getTitle(),
                doc.getBookmarkCount(),
                doc.getMainImgUrl(),
                doc.getSourceType() != null ? RecipeSource.valueOf(doc.getSourceType()) : null,
                doc.getAuthorName(),
                doc.getAuthorProfileImgUrl(),
                doc.getPlatform() != null ? SourcePlatform.valueOf(doc.getPlatform()) : null,
                doc.getSourceUrl(),
                doc.getCreatorName(),
                doc.getCreatorProfileImgUrl()
        );
    }
}
