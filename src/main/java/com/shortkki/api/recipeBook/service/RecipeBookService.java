package com.shortkki.api.recipeBook.service;

import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeBook.dto.RecipeBookCreateRequest;
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
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.event.DomainEventPublisher;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
        Member member = recipeBookValidationService.findMemberById(memberId);

        List<RecipeBook> existingBooks = recipeBookQueryService.findAllByMemberId(memberId);
        int nextSortOrder = existingBooks.stream()
                .mapToInt(RecipeBook::getSortOrder)
                .max()
                .orElse(0) + 1;

        RecipeBook recipeBook = RecipeBook.create(member, request.title(), false, nextSortOrder);
        RecipeBook saved = recipeBookRepository.save(recipeBook);

        return RecipeBookResponse.from(saved);
    }

    public List<RecipeBookResponse> findAllByMember(Long memberId) {
        List<RecipeBook> recipeBooks = recipeBookQueryService.findAllByMemberId(memberId);
        return toRecipeBookResponses(recipeBooks);
    }

    public List<RecipeBookResponse> findAllByGroup(Long memberId, Long groupId) {
        recipeBookValidationService.findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, groupId);
        List<RecipeBook> recipeBooks = recipeBookQueryService.findAllByGroupId(groupId);
        return toRecipeBookResponses(recipeBooks);
    }

    private List<RecipeBookResponse> toRecipeBookResponses(List<RecipeBook> recipeBooks) {
        if (recipeBooks.isEmpty()) {
            return List.of();
        }

        List<Long> recipeBookIds = recipeBooks.stream()
                .map(RecipeBook::getId)
                .toList();

        Map<Long, List<RecipeSummaryResponse>> recipesByBookId = recipeBookItemRepository
                .findAllByRecipeBookIdsWithRecipe(recipeBookIds)
                .stream()
                .collect(Collectors.groupingBy(
                        item -> item.getRecipeBook().getId(),
                        Collectors.mapping(
                                item -> RecipeSummaryResponse.from(item.getRecipe()),
                                Collectors.toList())));

        return recipeBooks.stream()
                .map(book -> RecipeBookResponse.from(
                        book,
                        recipesByBookId.getOrDefault(book.getId(), List.of())))
                .toList();
    }

    public RecipeBookResponse findById(Long memberId, Long id) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(id);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);

        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookIdWithRecipe(id);
        List<RecipeSummaryResponse> recipes = items.stream()
                .map(item -> RecipeSummaryResponse.from(item.getRecipe()))
                .toList();

        return RecipeBookResponse.from(recipeBook, recipes);
    }

    @Transactional
    public void updateTitle(Long memberId, Long id, RecipeBookUpdateRequest request) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(id);
        recipeBookValidationService.validateNotGroupRecipeBook(recipeBook);
        recipeBookValidationService.validateRecipeBookOwnership(recipeBook, memberId);

        recipeBook.updateTitle(request.title());
    }

    @Transactional
    public void delete(Long memberId, Long id) {
        RecipeBook recipeBook = validateDeleteAction(memberId, id);

        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookId(id);
        recipeBookItemRepository.deleteAllByRecipeBookId(id);

        decreaseBookmarkCounts(recipeBook, items);

        recipeBookRepository.delete(recipeBook);
    }

    private RecipeBook validateDeleteAction(Long memberId, Long recipeBookId) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateNotGroupRecipeBook(recipeBook);
        recipeBookValidationService.validateRecipeBookOwnership(recipeBook, memberId);
        recipeBookValidationService.validateNotDefaultRecipeBook(recipeBook);
        return recipeBook;
    }

    private void decreaseBookmarkCounts(RecipeBook recipeBook, List<RecipeBookItem> items) {
        if (items.isEmpty()) {
            return;
        }

        List<Long> recipeIds = items.stream()
                .map(item -> item.getRecipe().getId())
                .toList();

        if (recipeIds.isEmpty()) {
            return;
        }

        List<Long> otherBookmarkedIds = findBookmarkedRecipeIdsInOtherBooks(recipeBook, recipeIds);

        List<Long> recipeIdsToDecrement = recipeIds.stream()
                .filter(id -> !otherBookmarkedIds.contains(id))
                .toList();

        if (!recipeIdsToDecrement.isEmpty()) {
            recipeRepository.decrementBookmarkCountBulk(recipeIdsToDecrement);
        }
    }

    private List<Long> findBookmarkedRecipeIdsInOtherBooks(RecipeBook recipeBook, List<Long> recipeIds) {
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

    private void addRecipeInternal(Long memberId, Long recipeBookId, Long recipeId, boolean skipIfExists) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);
        Recipe recipe = recipeBookValidationService.findRecipeById(recipeId);

        if (skipIfExists && recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            return;
        }

        if (!skipIfExists) {
            recipeBookValidationService.validateRecipeNotInBook(recipeBookId, recipeId);
        }

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);

        boolean hasOtherBookmarks = recipeBook.getMemberId() != null
                ? recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(recipeBook.getMemberId(), recipeId,
                        recipeBookId)
                : recipeBookItemRepository.existsByGroupAndRecipeExcludingBook(recipeBook.getGroupId(), recipeId,
                        recipeBookId);

        if (!hasOtherBookmarks) {
            recipeRepository.incrementBookmarkCount(recipeId);
        }

        domainEventPublisher.publish(new RecipeIndexUpsertEvent(recipeId));

        if (recipeBook.getGroupId() != null) {
            domainEventPublisher.publish(new GroupRecipeAddedEvent(recipeBook.getGroupId(), memberId, recipeId));
        }
    }

    @Transactional
    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(recipeBookId);
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
        Member member = recipeBookValidationService.findMemberById(memberId);
        createDefaultForMember(member);
    }

    @Transactional
    public void createDefaultForGroup(Long groupId, String groupName) {
        recipeBookValidationService.findGroupById(groupId);

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

        recipeBookValidationService.validateReorderRequest(recipeBookIds, memberRecipeBooks);

        Map<Long, RecipeBook> recipeBookMap = memberRecipeBooks.stream()
                .collect(Collectors.toMap(RecipeBook::getId, book -> book));

        for (int i = 0; i < recipeBookIds.size(); i++) {
            RecipeBook book = recipeBookMap.get(recipeBookIds.get(i));
            book.updateSortOrder(i + 1);
        }

        recipeBookRepository.saveAll(recipeBookMap.values());
    }

    @Transactional
    public void moveRecipe(Long memberId, Long fromBookId, Long toBookId, Long recipeId) {
        if (fromBookId.equals(toBookId)) {
            return;
        }

        RecipeBook fromBook = recipeBookValidationService.findRecipeBookById(fromBookId);
        RecipeBook toBook = recipeBookValidationService.findRecipeBookById(toBookId);

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
