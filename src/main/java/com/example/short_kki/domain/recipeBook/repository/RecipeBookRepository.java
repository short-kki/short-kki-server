package com.example.short_kki.domain.recipeBook.repository;

import com.example.short_kki.domain.member.repository.MemberRepositoryCustom;
import com.example.short_kki.domain.recipeBook.entity.RecipeBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookRepository extends JpaRepository<RecipeBook, Long>,
        MemberRepositoryCustom {

    List<RecipeBook> findAllByMemberIdOrderBySortOrder(Long memberId);

    void deleteAllByMemberId(Long memberId);

    void deleteByGroupId(Long groupId);
}
