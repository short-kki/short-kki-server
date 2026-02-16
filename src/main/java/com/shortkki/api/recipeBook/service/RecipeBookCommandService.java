package com.shortkki.api.recipeBook.service;

import com.shortkki.api.feed.event.GroupRecipeAddedEvent;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipeBook.dto.RecipeBookCreateRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookReorderRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookUpdateRequest;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.api.recipeBook.repository.RecipeBookRepository;
import com.shortkki.api.search.event.RecipeIndexUpsertEvent;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.event.DomainEventPublisher;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeBookCommandService {

    private final RecipeBookQueryService recipeBookQueryService;
    private final RecipeBookValidationService recipeBookValidationService;
    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookItemRepository recipeBookItemRepository;
    private final RecipeBookBookmarkPolicy recipeBookBookmarkPolicy;
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
        List<Long> decrementedIds = recipeBookBookmarkPolicy.decreaseBookmarkCountsForDeletedBook(
                recipeBook, items);

        // TODO: 추후 검색 인덱싱 로직이 준비되면 주석 해제 (데이터 불일치 방지)
        /*
         * for (Long recipeId : decrementedIds) {
         * domainEventPublisher.publish(new RecipeIndexUpsertEvent(recipeId));
         * }
         */

        recipeBookRepository.delete(recipeBook);
    }

    @Transactional
    public void addRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        addRecipeInternal(memberId, recipeBookId, recipeId, false);
    }

    @Transactional
    public void addRecipeIfNotExists(Long memberId, Long recipeBookId, Long recipeId) {
        addRecipeInternal(memberId, recipeBookId, recipeId, true);
    }

    @Transactional
    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);
        recipeBookValidationService.validateRecipeInBook(recipeBookId, recipeId);

        long deleted = recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(recipeBookId,
                recipeId);
        recipeBookBookmarkPolicy.decreaseBookmarkCountIfLastOwnership(
                recipeBook, recipeId, recipeBookId, deleted);
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

        recipeBookBookmarkPolicy.increaseBookmarkCountIfFirstOwnership(recipeBook, recipeId,
                recipeBookId);

        domainEventPublisher.publish(new RecipeIndexUpsertEvent(recipeId));

        if (recipeBook.getGroupId() != null) {
            domainEventPublisher.publish(
                    new GroupRecipeAddedEvent(recipeBook.getGroupId(), memberId, recipeId));
        }
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
}
