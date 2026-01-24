package com.example.short_kki.domain.recipeBook.service;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.repository.MemberRepository;
import com.example.short_kki.domain.recipe.entity.Recipe;
import com.example.short_kki.domain.recipe.repository.RecipeRepository;
import com.example.short_kki.domain.recipeBook.dto.RecipeBookCreateRequest;
import com.example.short_kki.domain.recipeBook.dto.RecipeBookResponse;
import com.example.short_kki.domain.recipeBook.dto.RecipeBookUpdateRequest;
import com.example.short_kki.domain.recipeBook.dto.RecipeSummaryResponse;
import com.example.short_kki.domain.recipeBook.entity.RecipeBook;
import com.example.short_kki.domain.recipeBook.entity.RecipeBookItem;
import com.example.short_kki.domain.recipeBook.repository.RecipeBookItemRepository;
import com.example.short_kki.domain.recipeBook.repository.RecipeBookRepository;
import com.example.short_kki.global.exception.BusinessException;
import com.example.short_kki.global.exception.ErrorCode;
import java.util.List;
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

    @Transactional
    public RecipeBookResponse create(Long memberId, RecipeBookCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<RecipeBook> existingBooks = recipeBookRepository.findAllByMemberIdOrderBySortOrder(
                memberId);
        int nextSortOrder = existingBooks.size() + 1;

        RecipeBook recipeBook = RecipeBook.create(member, request.title(), false, nextSortOrder);
        RecipeBook saved = recipeBookRepository.save(recipeBook);

        return RecipeBookResponse.from(saved);
    }

    public List<RecipeBookResponse> findAllByMember(Long memberId) {
        List<RecipeBook> recipeBooks = recipeBookRepository.findAllByMemberIdOrderBySortOrder(
                memberId);
        return recipeBooks.stream()
                .map(RecipeBookResponse::from)
                .toList();
    }

    public RecipeBookResponse findById(Long memberId, Long id) {
        RecipeBook recipeBook = recipeBookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_BOOK_NOT_FOUND));

        if (!recipeBook.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookId(id);
        List<RecipeSummaryResponse> recipes = items.stream()
                .map(item -> RecipeSummaryResponse.from(item.getRecipe()))
                .toList();

        return RecipeBookResponse.from(recipeBook, recipes);
    }

    @Transactional
    public void updateTitle(Long memberId, Long id, RecipeBookUpdateRequest request) {
        RecipeBook recipeBook = recipeBookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_BOOK_NOT_FOUND));

        if (!recipeBook.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        recipeBook.updateTitle(request.title());
    }

    @Transactional
    public void delete(Long memberId, Long id) {
        RecipeBook recipeBook = recipeBookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_BOOK_NOT_FOUND));

        if (!recipeBook.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
        if (recipeBook.getIsDefault()) {
            throw new BusinessException(ErrorCode.CANNOT_DELETE_DEFAULT_RECIPE_BOOK);
        }
        recipeBookRepository.delete(recipeBook);
    }

    @Transactional
    public void addRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookRepository.findById(recipeBookId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_BOOK_NOT_FOUND));

        if (!recipeBook.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_NOT_FOUND));

        if (recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            throw new BusinessException(ErrorCode.RECIPE_ALREADY_IN_BOOK);
        }

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);
    }

    @Transactional
    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookRepository.findById(recipeBookId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RECIPE_BOOK_NOT_FOUND));

        if (!recipeBook.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        if (!recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            throw new BusinessException(ErrorCode.RECIPE_NOT_IN_BOOK);
        }

        recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(recipeBookId, recipeId);
    }

    @Transactional
    public void createDefaultForMember(Member member) {
        RecipeBook defaultBook = RecipeBook.create(member, "내 레시피북", true, 1);
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void createDefaultForGroup(Long groupId, String groupName) {
        RecipeBook defaultBook = RecipeBook.createForGroup(groupId, groupName + " 레시피북");
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void deleteAllByMemberId(Long memberId) {
        recipeBookRepository.deleteAllByMemberId(memberId);
    }

    @Transactional
    public void deleteByGroupId(Long groupId) {
        recipeBookRepository.deleteByGroupId(groupId);
    }
}
