package com.shortkki.api.recipebook;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.shortkki.api.feed.event.GroupRecipeAddedEvent;
import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.api.recipeBook.repository.RecipeBookRepository;
import com.shortkki.api.recipeBook.service.RecipeBookQueryService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.api.recipeBook.service.RecipeBookValidationService;
import com.shortkki.api.search.event.RecipeIndexUpsertEvent;
import com.shortkki.global.event.DomainEventPublisher;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeBookServiceTest {

    @InjectMocks
    private RecipeBookService recipeBookService;

    @Mock
    private RecipeBookQueryService recipeBookQueryService;
    @Mock
    private RecipeBookValidationService recipeBookValidationService;
    @Mock
    private GroupMemberValidationService groupMemberValidationService;
    @Mock
    private RecipeBookRepository recipeBookRepository;
    @Mock
    private RecipeBookItemRepository recipeBookItemRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private DomainEventPublisher domainEventPublisher;

    @Test
    @DisplayName("새 레시피 북마크 시 카운트 1 증가")
    void 새_레시피_북마크_시_카운트_증가() {
        // given
        Long memberId = 1L;
        Long bookIdA = 10L;
        Long recipeId = 100L;

        RecipeBook bookA = mock(RecipeBook.class);
        given(bookA.getMemberId()).willReturn(memberId);
        given(bookA.getGroupId()).willReturn(null);

        Recipe recipe = mock(Recipe.class);

        given(recipeBookQueryService.findRecipeBookById(bookIdA)).willReturn(bookA);
        given(recipeBookQueryService.findRecipeById(recipeId)).willReturn(recipe);
        given(recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(memberId, recipeId, bookIdA))
                .willReturn(false);

        // when
        recipeBookService.addRecipe(memberId, bookIdA, recipeId);

        // then
        verify(recipeRepository).incrementBookmarkCount(recipeId);
        verify(domainEventPublisher).publish(any(RecipeIndexUpsertEvent.class));
    }

    @Test
    @DisplayName("중복 북마크 시 카운트 변화 없음")
    void 중복_북마크_시_카운트_변화_없음() {
        // given
        Long memberId = 1L;
        Long bookIdB = 20L;
        Long recipeId = 100L;

        RecipeBook bookB = mock(RecipeBook.class);
        given(bookB.getMemberId()).willReturn(memberId);
        given(bookB.getGroupId()).willReturn(null);

        Recipe recipe = mock(Recipe.class);

        given(recipeBookQueryService.findRecipeBookById(bookIdB)).willReturn(bookB);
        given(recipeBookQueryService.findRecipeById(recipeId)).willReturn(recipe);
        given(recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(memberId, recipeId, bookIdB))
                .willReturn(true);

        // when
        recipeBookService.addRecipe(memberId, bookIdB, recipeId);

        // then
        verify(recipeRepository, never()).incrementBookmarkCount(recipeId);
        verify(domainEventPublisher).publish(any(RecipeIndexUpsertEvent.class));
    }

    @Test
    @DisplayName("부분 삭제 시 카운트 변화 없음")
    void 부분_삭제_시_카운트_변화_없음() {
        // given
        Long memberId = 1L;
        Long bookIdA = 10L;
        Long recipeId = 100L;

        RecipeBook bookA = mock(RecipeBook.class);
        given(bookA.getMemberId()).willReturn(memberId);

        given(recipeBookQueryService.findRecipeBookById(bookIdA)).willReturn(bookA);
        given(recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(memberId, recipeId, bookIdA))
                .willReturn(true);
        given(recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(bookIdA, recipeId)).willReturn(1L);

        // when
        recipeBookService.removeRecipe(memberId, bookIdA, recipeId);

        // then
        verify(recipeRepository, never()).decrementBookmarkCount(recipeId);
        verify(domainEventPublisher).publish(any(RecipeIndexUpsertEvent.class));
    }

    @Test
    @DisplayName("완전 삭제 시 카운트 1 감소")
    void 완전_삭제_시_카운트_감소() {
        // given
        Long memberId = 1L;
        Long bookIdB = 20L;
        Long recipeId = 100L;

        RecipeBook bookB = mock(RecipeBook.class);
        given(bookB.getMemberId()).willReturn(memberId);

        given(recipeBookQueryService.findRecipeBookById(bookIdB)).willReturn(bookB);
        given(recipeBookItemRepository.existsByMemberAndRecipeExcludingBook(memberId, recipeId, bookIdB))
                .willReturn(false);
        given(recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(bookIdB, recipeId)).willReturn(1L);

        // when
        recipeBookService.removeRecipe(memberId, bookIdB, recipeId);

        // then
        verify(recipeRepository).decrementBookmarkCount(recipeId);
        verify(domainEventPublisher).publish(any(RecipeIndexUpsertEvent.class));
    }

    @Test
    @DisplayName("레시피북 삭제 시 고유 레시피만 카운트 감소")
    void 레시피북_삭제_시_고유_레시피만_카운트_감소() {
        // given
        Long memberId = 1L;
        Long bookIdA = 10L;
        Long recipeId1 = 100L;
        Long recipeId2 = 200L;

        RecipeBook bookA = mock(RecipeBook.class);
        given(bookA.getId()).willReturn(bookIdA);
        given(bookA.getMemberId()).willReturn(memberId);

        given(recipeBookQueryService.findRecipeBookById(bookIdA)).willReturn(bookA);

        Recipe recipe1 = mock(Recipe.class);
        given(recipe1.getId()).willReturn(recipeId1);
        RecipeBookItem item1 = mock(RecipeBookItem.class);
        given(item1.getRecipe()).willReturn(recipe1);

        Recipe recipe2 = mock(Recipe.class);
        given(recipe2.getId()).willReturn(recipeId2);
        RecipeBookItem item2 = mock(RecipeBookItem.class);
        given(item2.getRecipe()).willReturn(recipe2);

        given(recipeBookItemRepository.findAllByRecipeBookId(bookIdA)).willReturn(List.of(item1, item2));
        // recipeId1은 다른 레시피북에도 존재, recipeId2는 이 레시피북에만 존재
        given(recipeBookItemRepository.findRecipeIdsBookmarkedByMemberExcludingBook(eq(memberId), anyList(),
                eq(bookIdA)))
                .willReturn(List.of(recipeId1));

        // when
        recipeBookService.delete(memberId, bookIdA);

        // then
        verify(recipeRepository, never()).decrementBookmarkCount(recipeId1);
        verify(recipeRepository).decrementBookmarkCountBulk(List.of(recipeId2));
        verify(recipeBookRepository).delete(bookA);
    }

    @Test
    @DisplayName("그룹 레시피북에 레시피 추가 시 이벤트 발행")
    void 그룹_레시피북에_레시피_추가_시_이벤트_발행() {
        // given
        Long memberId = 1L;
        Long groupId = 100L;
        Long recipeBookId = 30L;
        Long recipeId = 500L;

        RecipeBook groupRecipeBook = mock(RecipeBook.class);
        given(groupRecipeBook.getGroupId()).willReturn(groupId);
        given(groupRecipeBook.getMemberId()).willReturn(null);

        Recipe recipe = mock(Recipe.class);

        given(recipeBookQueryService.findRecipeBookById(recipeBookId)).willReturn(groupRecipeBook);
        given(recipeBookQueryService.findRecipeById(recipeId)).willReturn(recipe);
        given(recipeBookItemRepository.existsByGroupAndRecipeExcludingBook(groupId, recipeId, recipeBookId))
                .willReturn(false);

        // when
        recipeBookService.addRecipe(memberId, recipeBookId, recipeId);

        // then
        verify(domainEventPublisher).publish(any(RecipeIndexUpsertEvent.class));
        verify(domainEventPublisher).publish(any(GroupRecipeAddedEvent.class));
    }
}
