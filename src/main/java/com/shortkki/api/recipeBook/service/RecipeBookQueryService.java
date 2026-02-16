package com.shortkki.api.recipeBook.service;

import static com.shortkki.api.recipeBook.entity.QRecipeBook.recipeBook;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.repository.RecipeBookRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeBookQueryService {

    private final JPAQueryFactory queryFactory;
    private final RecipeBookRepository recipeBookRepository;

    public List<RecipeBook> findAllByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(recipeBook)
                .where(recipeBook.member.id.eq(memberId))
                .orderBy(recipeBook.sortOrder.asc())
                .fetch();
    }

    public Slice<RecipeBook> findSliceByMemberId(Long memberId, Pageable pageable) {
        List<RecipeBook> books = queryFactory
                .selectFrom(recipeBook)
                .where(recipeBook.member.id.eq(memberId))
                .orderBy(recipeBook.sortOrder.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = books.size() > pageable.getPageSize();
        List<RecipeBook> content = hasNext ? books.subList(0, pageable.getPageSize()) : books;
        return new SliceImpl<>(content, pageable, hasNext);
    }

    public Slice<RecipeBook> findSliceByGroupId(Long groupId, Pageable pageable) {
        List<RecipeBook> books = queryFactory
                .selectFrom(recipeBook)
                .where(recipeBook.groupId.eq(groupId))
                .orderBy(recipeBook.title.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = books.size() > pageable.getPageSize();
        List<RecipeBook> content = hasNext ? books.subList(0, pageable.getPageSize()) : books;
        return new SliceImpl<>(content, pageable, hasNext);
    }

    public Optional<RecipeBook> findDefaultByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(recipeBook)
                        .where(
                                recipeBook.member.id.eq(memberId),
                                recipeBook.isDefault.isTrue())
                        .fetchOne());
    }

    public Optional<RecipeBook> findDefaultByGroupId(Long groupId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(recipeBook)
                        .where(
                                recipeBook.groupId.eq(groupId),
                                recipeBook.isDefault.isTrue())
                        .fetchOne());
    }

    public RecipeBook findRecipeBookById(Long recipeBookId) {
        return recipeBookRepository.findById(recipeBookId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_BOOK_NOT_FOUND));
    }
}
