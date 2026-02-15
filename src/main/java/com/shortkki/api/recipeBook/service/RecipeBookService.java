package com.shortkki.api.recipeBook.service;

import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeBook.dto.RecipeBookCreateRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookDetailResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookListResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookRecipeSortType;
import com.shortkki.api.recipeBook.dto.RecipeBookReorderRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookUpdateRequest;
import com.shortkki.api.recipeBook.dto.RecipeSummaryResponse;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.api.recipeBook.repository.RecipeBookRepository;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.search.event.RecipeIndexUpsertEvent;
import com.shortkki.api.feed.event.GroupRecipeAddedEvent;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.event.DomainEventPublisher;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeBookService {

    private final RecipeBookQueryService recipeBookQueryService;
    private final RecipeBookValidationService recipeBookValidationService;
    private final GroupMemberValidationService groupMemberValidationService;

    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookItemRepository recipeBookItemRepository;
    private final RecipeRepository recipeRepository;

    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public RecipeBookResponse create(Long memberId, RecipeBookCreateRequest request) {
        Member member = recipeBookQueryService.findMemberById(memberId);

        List<RecipeBook> existingBooks = recipeBookQueryService.findAllByMemberId(memberId);
        int nextSortOrder = existingBooks.stream()
                .mapToInt(RecipeBook::getSortOrder)
                .max()
                .orElse(0) + 1;

        RecipeBook recipeBook = RecipeBook.create(member, request.title(), false, nextSortOrder);
        RecipeBook saved = recipeBookRepository.save(recipeBook);

        return RecipeBookResponse.from(saved);
    }

    public RecipeBookListResponse findAllByMember(Long memberId, Pageable pageable) {
        List<RecipeBook> recipeBooks = recipeBookQueryService.findAllByMemberId(memberId);
        return buildRecipeBookListResponse(recipeBooks, pageable);
    }

    public List<Long> findOwnedRecipeBookIdsByRecipe(Long memberId, Long recipeId) {
        return recipeBookItemRepository.findOwnedRecipeBookIdsByRecipeIdAndMemberId(recipeId,
                memberId);
    }

    public RecipeBookListResponse findAllByGroup(Long memberId, Long groupId, Pageable pageable) {
        validateGroupMembership(memberId, groupId);
        List<RecipeBook> recipeBooks = recipeBookQueryService.findAllByGroupId(groupId);
        return buildRecipeBookListResponse(recipeBooks, pageable);
    }

    private void validateGroupMembership(Long memberId, Long groupId) {
        recipeBookQueryService.findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, groupId);
    }

    private RecipeBookListResponse buildRecipeBookListResponse(List<RecipeBook> recipeBooks,
            Pageable pageable) {
        if (recipeBooks.isEmpty()) {
            return new RecipeBookListResponse(List.of(), null);
        }

        List<Long> recipeBookIds = extractRecipeBookIds(recipeBooks);
        Slice<RecipeBookItem> slice = recipeBookItemRepository.findAllByRecipeBookIdsWithRecipe(
                recipeBookIds, pageable);
        Map<Long, List<RecipeSummaryResponse>> recipesByBookId = mapRecipesByBookId(slice);
        Map<Long, Long> recipeCountByBookId = recipeBookItemRepository.countByRecipeBookIds(
                recipeBookIds);

        SlicePageInfoResponse pageInfo = SlicePageInfoResponse.from(slice);
        List<RecipeBookResponse> responses = recipeBooks.stream()
                .map(book -> buildRecipeBookSummaryResponse(book, recipesByBookId,
                        recipeCountByBookId))
                .toList();

        return new RecipeBookListResponse(responses, pageInfo);
    }

    private List<Long> extractRecipeBookIds(List<RecipeBook> recipeBooks) {
        return recipeBooks.stream()
                .map(RecipeBook::getId)
                .toList();
    }

    private Map<Long, List<RecipeSummaryResponse>> mapRecipesByBookId(Slice<RecipeBookItem> slice) {
        return slice.getContent().stream()
                .collect(Collectors.groupingBy(
                        item -> item.getRecipeBook().getId(),
                        Collectors.mapping(
                                item -> RecipeSummaryResponse.from(item.getRecipe()),
                                Collectors.toList())));
    }

    private RecipeBookResponse buildRecipeBookSummaryResponse(
            RecipeBook recipeBook,
            Map<Long, List<RecipeSummaryResponse>> recipesByBookId,
            Map<Long, Long> recipeCountByBookId
    ) {
        long recipeCount = recipeCountByBookId.getOrDefault(recipeBook.getId(), 0L);
        return RecipeBookResponse.from(
                recipeBook,
                recipesByBookId.getOrDefault(recipeBook.getId(), List.of()),
                recipeCount);
    }

    public RecipeBookDetailResponse findById(
            Long memberId,
            Long id,
            Pageable pageable,
            RecipeBookRecipeSortType sortType
    ) {
        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(id);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);

        Slice<RecipeBookItem> slice = findRecipeItemsBySort(id, pageable, sortType);
        List<RecipeSummaryResponse> recipes = slice.getContent().stream()
                .map(item -> RecipeSummaryResponse.from(item.getRecipe()))
                .toList();
        long recipeCount = recipeBookItemRepository.countByRecipeBookId(id);

        RecipeBookResponse response = RecipeBookResponse.from(recipeBook, recipes, recipeCount);
        return new RecipeBookDetailResponse(response, SlicePageInfoResponse.from(slice));
    }

    @Transactional
    public void updateTitle(Long memberId, Long id, RecipeBookUpdateRequest request) {
        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(id);
        recipeBookValidationService.validateNotGroupRecipeBook(recipeBook);
        recipeBookValidationService.validateRecipeBookOwnership(recipeBook, memberId);

        recipeBook.updateTitle(request.title());
    }

    @Transactional
    public void delete(Long memberId, Long id) {
        recipeBookValidationService.validateDeletable(memberId, id);

        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(id);
        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookId(id);
        recipeBookItemRepository.deleteAllByRecipeBookId(id);

        @SuppressWarnings("unused")
        List<Long> decrementedIds = decreaseBookmarkCounts(recipeBook, items);

        // TODO: 추후 검색 인덱싱 로직이 준비되면 주석 해제 (데이터 불일치 방지)
        /*
         * for (Long recipeId : decrementedIds) {
         * domainEventPublisher.publish(new RecipeIndexUpsertEvent(recipeId));
         * }
         */

        recipeBookRepository.delete(recipeBook);
    }

    private List<Long> decreaseBookmarkCounts(RecipeBook recipeBook, List<RecipeBookItem> items) {
        if (items.isEmpty()) {
            return List.of();
        }

        List<Long> recipeIds = items.stream()
                .map(item -> item.getRecipe().getId())
                .toList();

        if (recipeIds.isEmpty()) {
            return List.of();
        }

        List<Long> otherBookmarkedIds = findBookmarkedRecipeIdsInOtherBooks(recipeBook, recipeIds);
        Set<Long> otherBookmarkedIdSet = new HashSet<>(otherBookmarkedIds);

        List<Long> recipeIdsToDecrement = recipeIds.stream()
                .filter(id -> !otherBookmarkedIdSet.contains(id))
                .toList();

        if (!recipeIdsToDecrement.isEmpty()) {
            recipeRepository.decrementBookmarkCountBulk(recipeIdsToDecrement);
        }

        return recipeIdsToDecrement;
    }

    private List<Long> findBookmarkedRecipeIdsInOtherBooks(RecipeBook recipeBook,
            List<Long> recipeIds) {
        if (recipeBook.getMemberId() != null) {
            return recipeBookItemRepository.findRecipeIdsBookmarkedByMemberExcludingBook(
                    recipeBook.getMemberId(), recipeIds, recipeBook.getId());
        }
        return recipeBookItemRepository.findRecipeIdsBookmarkedByGroupExcludingBook(
                recipeBook.getGroupId(), recipeIds, recipeBook.getId());
    }

    @Transactional
    public void addRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        addRecipeInternal(memberId, recipeBookId, recipeId, false);
    }

    @Transactional
    public void addRecipeIfNotExists(Long memberId, Long recipeBookId, Long recipeId) {
        addRecipeInternal(memberId, recipeBookId, recipeId, true);
    }

    private void addRecipeInternal(Long memberId, Long recipeBookId, Long recipeId,
            boolean skipIfExists) {
        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);
        Recipe recipe = recipeBookQueryService.findRecipeById(recipeId);

        if (skipIfExists && recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId,
                recipeId)) {
            return;
        }

        if (!skipIfExists) {
            recipeBookValidationService.validateRecipeNotInBook(recipeBookId, recipeId);
        }

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);

        boolean hasOtherBookmarks = recipeBook.getMemberId() != null
                ? recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(
                        recipeBook.getMemberId(), recipeId,
                        recipeBookId)
                : recipeBookItemRepository.existsByGroupAndRecipeExcludingBook(
                        recipeBook.getGroupId(), recipeId,
                        recipeBookId);

        if (!hasOtherBookmarks) {
            recipeRepository.incrementBookmarkCount(recipeId);
        }

        domainEventPublisher.publish(new RecipeIndexUpsertEvent(recipeId));

        if (recipeBook.getGroupId() != null) {
            domainEventPublisher.publish(
                    new GroupRecipeAddedEvent(recipeBook.getGroupId(), memberId, recipeId));
        }
    }

    @Transactional
    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);
        recipeBookValidationService.validateRecipeInBook(recipeBookId, recipeId);

        boolean hasOtherBookmarks;
        if (recipeBook.getMemberId() != null) {
            hasOtherBookmarks = recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(
                    recipeBook.getMemberId(), recipeId, recipeBookId);
        } else {
            hasOtherBookmarks = recipeBookItemRepository.existsByGroupAndRecipeExcludingBook(
                    recipeBook.getGroupId(), recipeId, recipeBookId);
        }

        long deleted = recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(recipeBookId,
                recipeId);
        if (deleted > 0 && !hasOtherBookmarks) {
            recipeRepository.decrementBookmarkCount(recipeId);
        }
        domainEventPublisher.publish(new RecipeIndexUpsertEvent(recipeId));
    }

    @Transactional
    public void createDefaultForMember(Member member) {
        if (recipeBookQueryService.findDefaultByMemberId(member.getId()).isPresent()) {
            return;
        }

        RecipeBook defaultBook = RecipeBook.create(member, "내 레시피북", true, 1);
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void createDefaultForMember(Long memberId) {
        Member member = recipeBookQueryService.findMemberById(memberId);
        createDefaultForMember(member);
    }

    @Transactional
    public void createDefaultForGroup(Long groupId, String groupName) {
        recipeBookQueryService.findGroupById(groupId);

        if (recipeBookQueryService.findDefaultByGroupId(groupId).isPresent()) {
            return;
        }

        RecipeBook defaultBook = RecipeBook.createForGroup(groupId, groupName + " 레시피북");
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void reorder(Long memberId, RecipeBookReorderRequest request) {
        List<Long> recipeBookIds = request.recipeBookIds();
        List<RecipeBook> memberRecipeBooks = recipeBookQueryService.findAllByMemberId(memberId);
        validateMemberOwnsRequestedBooks(recipeBookIds, memberRecipeBooks);

        List<RecipeBook> fixedBooks = memberRecipeBooks.stream()
                .filter(RecipeBook::getIsDefault)
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .toList();

        List<RecipeBook> mutableBooks = memberRecipeBooks.stream()
                .filter(book -> !book.getIsDefault())
                .toList();

        Set<Long> mutableBookIdSet = mutableBooks.stream()
                .map(RecipeBook::getId)
                .collect(Collectors.toSet());

        List<Long> mutableOrder = recipeBookIds.stream()
                .filter(mutableBookIdSet::contains)
                .toList();

        recipeBookValidationService.validateReorderable(mutableOrder, mutableBooks);

        Map<Long, RecipeBook> recipeBookMap = memberRecipeBooks.stream()
                .collect(Collectors.toMap(RecipeBook::getId, book -> book));

        int nextSortOrder = 1;
        for (RecipeBook fixedBook : fixedBooks) {
            fixedBook.updateSortOrder(nextSortOrder++);
        }

        for (int i = 0; i < mutableOrder.size(); i++) {
            RecipeBook book = recipeBookMap.get(mutableOrder.get(i));
            book.updateSortOrder(nextSortOrder + i);
        }

        recipeBookRepository.saveAll(recipeBookMap.values());
    }

    private void validateMemberOwnsRequestedBooks(List<Long> requestedBookIds,
            List<RecipeBook> memberRecipeBooks) {
        Set<Long> memberBookIdSet = memberRecipeBooks.stream()
                .map(RecipeBook::getId)
                .collect(Collectors.toSet());

        if (requestedBookIds.stream().anyMatch(id -> !memberBookIdSet.contains(id))) {
            throw new BadRequestException(ErrorCode.INVALID_REORDER_REQUEST);
        }
    }

    private Slice<RecipeBookItem> findRecipeItemsBySort(
            Long recipeBookId,
            Pageable pageable,
            RecipeBookRecipeSortType sortType
    ) {
        if (sortType == null) {
            return recipeBookItemRepository.findAllByRecipeBookIdWithRecipe(recipeBookId, pageable);
        }
        return switch (sortType) {
            case RECENT -> recipeBookItemRepository.findAllByRecipeBookIdWithRecipe(recipeBookId,
                    pageable);
            case OLDEST -> recipeBookItemRepository.findAllByRecipeBookIdWithRecipeOldest(
                    recipeBookId, pageable);
            case BOOKMARK_DESC ->
                    recipeBookItemRepository.findAllByRecipeBookIdWithRecipeByBookmarkDesc(
                            recipeBookId, pageable);
        };
    }

    @Transactional
    public void moveRecipe(Long memberId, Long fromBookId, Long toBookId, Long recipeId) {
        if (fromBookId.equals(toBookId)) {
            return;
        }

        RecipeBook fromBook = recipeBookQueryService.findRecipeBookById(fromBookId);
        RecipeBook toBook = recipeBookQueryService.findRecipeBookById(toBookId);

        recipeBookValidationService.validateRecipeBookAccess(fromBook, memberId);
        recipeBookValidationService.validateRecipeBookAccess(toBook, memberId);

        recipeBookValidationService.validateRecipeNotInBook(toBookId, recipeId);

        recipeBookValidationService.validateRecipeInBook(fromBookId, recipeId);

        long updated = recipeBookItemRepository.moveRecipe(fromBook, toBook, recipeId);

        if (updated == 0) {
            throw new NotFoundException(ErrorCode.RECIPE_NOT_IN_BOOK);
        }
    }

}
