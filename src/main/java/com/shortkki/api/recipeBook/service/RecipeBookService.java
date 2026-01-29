package com.shortkki.api.recipeBook.service;

import com.shortkki.api.recipe.entity.Recipe;
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
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.HashSet;
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
public class RecipeBookService {

    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookItemRepository recipeBookItemRepository;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final RecipeBookQueryService queryService;

    @Transactional
    public RecipeBookResponse create(Long memberId, RecipeBookCreateRequest request) {
        Member member = findMemberById(memberId);

        List<RecipeBook> existingBooks = queryService.findAllByMemberId(memberId);
        int nextSortOrder = existingBooks.stream()
                .mapToInt(RecipeBook::getSortOrder)
                .max()
                .orElse(0) + 1;

        RecipeBook recipeBook = RecipeBook.create(member, request.title(), false, nextSortOrder);
        RecipeBook saved = recipeBookRepository.save(recipeBook);

        return RecipeBookResponse.from(saved);
    }

    public List<RecipeBookResponse> findAllByMember(Long memberId) {
        List<RecipeBook> recipeBooks = queryService.findAllByMemberId(memberId);
        return recipeBooks.stream()
                .map(RecipeBookResponse::from)
                .toList();
    }

    public List<RecipeBookResponse> findAllByGroup(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        List<RecipeBook> recipeBooks = queryService.findAllByGroupId(groupId);
        return recipeBooks.stream()
                .map(RecipeBookResponse::from)
                .toList();
    }

    public RecipeBookResponse findById(Long memberId, Long id) {
        RecipeBook recipeBook = findRecipeBookById(id);
        validateRecipeBookOwnership(recipeBook, memberId);

        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookId(id);
        List<RecipeSummaryResponse> recipes = items.stream()
                .map(item -> RecipeSummaryResponse.from(item.getRecipe()))
                .toList();

        return RecipeBookResponse.from(recipeBook, recipes);
    }

    @Transactional
    public void updateTitle(Long memberId, Long id, RecipeBookUpdateRequest request) {
        RecipeBook recipeBook = findRecipeBookById(id);
        validateRecipeBookOwnership(recipeBook, memberId);

        recipeBook.updateTitle(request.title());
    }

    @Transactional
    public void delete(Long memberId, Long id) {
        RecipeBook recipeBook = findRecipeBookById(id);
        validateRecipeBookOwnership(recipeBook, memberId);
        validateNotDefaultRecipeBook(recipeBook);

        recipeBookItemRepository.deleteAllByRecipeBookId(id);
        recipeBookRepository.delete(recipeBook);
    }

    @Transactional
    public void addRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = findRecipeBookById(recipeBookId);
        validateRecipeBookOwnership(recipeBook, memberId);

        Recipe recipe = findRecipeById(recipeId);
        validateRecipeNotInBook(recipeBookId, recipeId);

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);
    }

    @Transactional
    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = findRecipeBookById(recipeBookId);
        validateRecipeBookOwnership(recipeBook, memberId);
        validateRecipeInBook(recipeBookId, recipeId);

        recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(recipeBookId, recipeId);
    }

    @Transactional
    public void createDefaultForMember(Member member) {
        boolean hasDefault = queryService.findAllByMemberId(member.getId()).stream()
                .anyMatch(RecipeBook::getIsDefault);

        if (hasDefault) {
            return;
        }

        RecipeBook defaultBook = RecipeBook.create(member, "내 레시피북", true, 1);
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void createDefaultForMember(Long memberId) {
        Member member = findMemberById(memberId);
        createDefaultForMember(member);
    }

    @Transactional
    public void createDefaultForGroup(Long groupId, String groupName) {
        findGroupById(groupId);

        boolean hasDefault = queryService.findAllByGroupId(groupId).stream()
                .anyMatch(RecipeBook::getIsDefault);

        if (hasDefault) {
            return;
        }

        RecipeBook defaultBook = RecipeBook.createForGroup(groupId, groupName + " 레시피북");
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void reorder(Long memberId, RecipeBookReorderRequest request) {
        List<Long> recipeBookIds = request.recipeBookIds();
        List<RecipeBook> memberRecipeBooks = queryService.findAllByMemberId(memberId);

        validateReorderRequest(recipeBookIds, memberRecipeBooks);

        Map<Long, RecipeBook> recipeBookMap = memberRecipeBooks.stream()
                .collect(Collectors.toMap(RecipeBook::getId, book -> book));

        for (int i = 0; i < recipeBookIds.size(); i++) {
            RecipeBook book = recipeBookMap.get(recipeBookIds.get(i));
            book.updateSortOrder(i + 1);
        }

        recipeBookRepository.saveAll(recipeBookMap.values());
    }

    private void validateReorderRequest(List<Long> requestIds, List<RecipeBook> memberBooks) {
        if (requestIds.size() != memberBooks.size()) {
            throw new BadRequestException(ErrorCode.INVALID_REORDER_REQUEST);
        }

        Set<Long> requestIdSet = new HashSet<>(requestIds);
        if (requestIdSet.size() != requestIds.size()) {
            throw new BadRequestException(ErrorCode.INVALID_REORDER_REQUEST);
        }

        Set<Long> memberBookIds = memberBooks.stream()
                .map(RecipeBook::getId)
                .collect(Collectors.toSet());

        if (!requestIdSet.equals(memberBookIds)) {
            throw new BadRequestException(ErrorCode.INVALID_REORDER_REQUEST);
        }
    }

    private void validateGroupMember(Long memberId, Group group) {
        if (!groupMemberRepository.existsByMemberIdAndGroup(memberId, group)) {
            throw new AccessDeniedException(ErrorCode.GROUP_NOT_MEMBER);
        }
    }

    private void validateRecipeBookOwnership(RecipeBook recipeBook, Long memberId) {
        if (!recipeBook.isOwnedByMember(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private RecipeBook findRecipeBookById(Long recipeBookId) {
        return recipeBookRepository.findById(recipeBookId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_BOOK_NOT_FOUND));
    }

    private Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_NOT_FOUND));
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_NOT_FOUND));
    }

    private void validateNotDefaultRecipeBook(RecipeBook recipeBook) {
        if (recipeBook.getIsDefault()) {
            throw new BadRequestException(ErrorCode.CANNOT_DELETE_DEFAULT_RECIPE_BOOK);
        }
    }

    private void validateRecipeNotInBook(Long recipeBookId, Long recipeId) {
        if (recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            throw new BadRequestException(ErrorCode.RECIPE_ALREADY_IN_BOOK);
        }
    }

    private void validateRecipeInBook(Long recipeBookId, Long recipeId) {
        if (!recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            throw new NotFoundException(ErrorCode.RECIPE_NOT_IN_BOOK);
        }
    }
}
