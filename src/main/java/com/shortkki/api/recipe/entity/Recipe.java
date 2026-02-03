package com.shortkki.api.recipe.entity;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.recipe.entity.vo.RecipeBasicInfo;
import com.shortkki.api.recipe.entity.vo.RecipeCategoryInfo;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceContentType;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.global.entity.BaseEntity;
import com.shortkki.global.error.exception.InvalidStateException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "recipe")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_content_id")
    private SourceContent sourceContent;

    @Embedded
    private RecipeBasicInfo basicInfo;

    @Embedded
    private RecipeCategoryInfo categoryInfo;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer bookmarkCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RecipeSource sourceType;

    @Column(name = "image_file_id")
    private Long imageFileId;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Builder
    private Recipe(
            Member member,
            RecipeBasicInfo basicInfo,
            RecipeCategoryInfo categoryInfo,
            Integer bookmarkCount,
            RecipeSource sourceType, SourceContent sourceContent, Boolean isDeleted) {
        this.member = member;
        this.basicInfo = basicInfo;
        this.categoryInfo = categoryInfo;
        this.sourceType = sourceType;
        this.sourceContent = sourceContent;
        this.bookmarkCount = bookmarkCount;
        this.isDeleted = isDeleted;
    }

    public static Recipe createManual(
            Member member,
            RecipeBasicInfo basicInfo,
            RecipeCategoryInfo categoryInfo) {
        return Recipe.builder()
                .member(member)
                .basicInfo(basicInfo)
                .categoryInfo(categoryInfo)
                .sourceType(RecipeSource.USER)
                .build();
    }

    public static Recipe createImported(
            Member member,
            RecipeBasicInfo basicInfo,
            RecipeCategoryInfo categoryInfo,
            SourceContent sourceContent) {
        if (sourceContent == null) {
            throw new InvalidStateException("원본 컨텐츠 정보 없이 외부 레시피를 생성할 수 없습니다.");
        }

        return Recipe.builder()
                .member(member)
                .basicInfo(basicInfo)
                .categoryInfo(categoryInfo)
                .sourceType(RecipeSource.IMPORT)
                .isDeleted(false)
                .bookmarkCount(0)
                .sourceContent(sourceContent)
                .build();
    }

    public void updateBasicInfo(RecipeBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void updateCategoryInfo(RecipeCategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public String getSourceUrl() {
        return RecipeSource.IMPORT == sourceType ? sourceContent.getCanonicalUrl() : null;
    }

    public SourcePlatform getSourcePlatform() {
        return RecipeSource.IMPORT == sourceType ? sourceContent.getPlatform() : null;
    }

    public SourceContentType getSourceContentType() {
        return RecipeSource.IMPORT == sourceType ? sourceContent.getContentType() : null;
    }

    public void setImageFileId(Long imageFileId) {
        this.imageFileId = imageFileId;
    }

    public void incrementBookmarkCount() {
        this.bookmarkCount++;
    }

    public void decrementBookmarkCount() {
        if (this.bookmarkCount > 0) {
            this.bookmarkCount--;
        }
    }
}
