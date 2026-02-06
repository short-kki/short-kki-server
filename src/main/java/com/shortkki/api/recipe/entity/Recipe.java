package com.shortkki.api.recipe.entity;

import com.shortkki.api.file.entity.FileMetadata;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_content_id", unique = true)
    private SourceContent sourceContent;

    @Embedded
    private RecipeBasicInfo basicInfo;

    @Embedded
    private RecipeCategoryInfo categoryInfo;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RecipeSource sourceType;

    @Builder.Default
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer bookmarkCount = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_img_file_id")
    private FileMetadata mainImgFile;

    @Builder.Default
    @ColumnDefault("true")
    @Column(nullable = false)
    private Boolean isActive = true;

    public static Recipe createManual(
            Member member,
            RecipeBasicInfo basicInfo,
            RecipeCategoryInfo categoryInfo
    ) {
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
            SourceContent sourceContent
    ) {
        if (sourceContent == null) {
            throw new InvalidStateException("원본 컨텐츠 정보 없이 외부 레시피를 생성할 수 없습니다.");
        }

        return Recipe.builder()
                .member(member)
                .basicInfo(basicInfo)
                .categoryInfo(categoryInfo)
                .sourceType(RecipeSource.IMPORT)
                .sourceContent(sourceContent)
                .build();
    }

    public void updateBasicInfo(RecipeBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void updateCategoryInfo(RecipeCategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public void updateMainImgFile(FileMetadata mainImgFile) {
        this.mainImgFile = mainImgFile;
    }

    public String getSourceUrl() {
        return isImported() ? sourceContent.getCanonicalUrl() : null;
    }

    public SourcePlatform getSourcePlatform() {
        return isImported() ? sourceContent.getPlatform() : null;
    }

    public SourceContentType getSourceContentType() {
        return isImported() ? sourceContent.getContentType() : null;
    }

    public String getAuthorName() {
        return this.getMember().getName();
    }

    public String getAuthorProfileImgUrl() {
        return this.getMember().getProfileImgUrl();
    }

    public String getCreatorName() {
        return isImported() ? this.getSourceContent().getSourceCreator().getDisplayName() : null;
    }

    public String getCreatorProfileImgUrl() {
        return isImported() ? this.getSourceContent().getSourceCreator().getProfileImgUrl() : null;
    }

    public String getMainImgUrl() {
        if (isImported()) {
            return sourceContent.getThumbnailUrl();
        }
        if (mainImgFile != null) {
            return mainImgFile.getUrl();
        }
        return null;
    }

    public boolean isImported() {
        return RecipeSource.IMPORT.equals(this.sourceType);
    }
}
