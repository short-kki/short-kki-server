package com.shortkki.api.recipeBook.service;

import com.shortkki.api.recipeBook.dto.RecipeBookListResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeSummaryResponse;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeBookListResponseAssembler {

    private static final int PREVIEW_ITEM_LIMIT = 3;

    private final RecipeBookItemRepository recipeBookItemRepository;

    public RecipeBookListResponse assemble(Slice<RecipeBook> recipeBookSlice) {
        List<RecipeBook> recipeBooks = recipeBookSlice.getContent();
        if (recipeBooks.isEmpty()) {
            return new RecipeBookListResponse(List.of(), SlicePageInfoResponse.from(recipeBookSlice));
        }

        List<Long> recipeBookIds = recipeBooks.stream()
                .map(RecipeBook::getId)
                .toList();

        List<RecipeBookItem> previewItems = recipeBookItemRepository.findPreviewItemsByRecipeBookIds(
                recipeBookIds, PREVIEW_ITEM_LIMIT);

        Map<Long, List<RecipeSummaryResponse>> recipesByBookId = previewItems.stream()
                .collect(Collectors.groupingBy(
                        item -> item.getRecipeBook().getId(),
                        Collectors.mapping(
                                item -> RecipeSummaryResponse.from(item.getRecipe()),
                                Collectors.toList())));

        Map<Long, Long> recipeCountByBookId = recipeBookItemRepository.countByRecipeBookIds(
                recipeBookIds);

        List<RecipeBookResponse> responses = recipeBooks.stream()
                .map(book -> RecipeBookResponse.from(
                        book,
                        recipesByBookId.getOrDefault(book.getId(), List.of()),
                        recipeCountByBookId.getOrDefault(book.getId(), 0L)))
                .toList();

        return new RecipeBookListResponse(responses, SlicePageInfoResponse.from(recipeBookSlice));
    }
}
