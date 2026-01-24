package com.example.short_kki.domain.recipeBook.entity;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.global.entity.BaseEntity;
import com.fasterxml.classmate.AnnotationOverrides.StdBuilder;
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
    private RecipeBook(Member member, Long groupId, String title, Boolean isDefault, Integer sortOrder) {
        this.member = member;
        this.groupId = groupId;
        this.title = title;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    public static RecipeBook create(Member member, String title, Boolean isDefault,
            Integer sortOrder) {
        return RecipeBook.builder()
                .member(member)
                .title(title)
                .isDefault(isDefault)
                .sortOrder(sortOrder)
                .build();
    }

    public static RecipeBook createForGroup(Long groupId, String title) {
        return RecipeBook.builder()
                .groupId(groupId)
                .title(title)
                .isDefault(true)
                .sortOrder(1)
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
