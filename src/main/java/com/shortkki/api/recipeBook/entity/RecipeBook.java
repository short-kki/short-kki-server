package com.shortkki.api.recipeBook.entity;

import com.shortkki.api.member.entity.Member;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "recipe_book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "group_id")
    private Long groupId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, updatable = false)
    private Boolean isDefault;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer sortOrder;

    @Builder
    private RecipeBook(Member member, Long groupId, String title, Boolean isDefault,
            Integer sortOrder) {
        this.member = member;
        this.groupId = groupId;
        this.title = title;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    public static RecipeBook create(Member member, String title, Boolean isDefault,
            Integer sortOrder) {
        RecipeBook recipeBook = RecipeBook.builder()
                .member(member)
                .title(title)
                .isDefault(isDefault)
                .sortOrder(sortOrder)
                .build();
        recipeBook.validateOwnership();
        return recipeBook;
    }

    public static RecipeBook createForGroup(Long groupId, String title) {
        RecipeBook recipeBook = RecipeBook.builder()
                .groupId(groupId)
                .title(title)
                .isDefault(true)
                .sortOrder(1)
                .build();
        recipeBook.validateOwnership();
        return recipeBook;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void validateOwnership() {
        if ((member == null && groupId == null) || (member != null && groupId != null)) {
            throw new IllegalStateException("레시피북은 그룹 또는 사용자 하나에만 소속될수 있다.");
        }
    }

    public Long getMemberId() {
        return (member != null) ? member.getId() : null;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public boolean isGroupRecipeBook() {
        return this.groupId != null;
    }

    public boolean isMemberRecipeBook() {
        return this.member != null;
    }

    public boolean isOwnedByMember(Long memberId) {
        return this.member != null && this.member.getId().equals(memberId);
    }
}
