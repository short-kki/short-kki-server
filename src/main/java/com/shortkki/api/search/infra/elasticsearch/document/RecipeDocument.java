package com.shortkki.api.search.infra.elasticsearch.document;

import java.time.LocalDate;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "recipes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RecipeDocument {

    @Id
    private Long id;

    /**
     * 기본 정보
     */
    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String sourceType;

    @Field(type = FieldType.Integer)
    private int bookmarkCount;

    @Field(type = FieldType.Keyword)
    private String mainImgUrl;

    /**
     * 카테고리
      */
    @Field(type = FieldType.Keyword)
    private String cuisineType;

    @Field(type = FieldType.Keyword)
    private String mealType;

    @Field(type = FieldType.Keyword)
    private String difficulty;

    @Field(type = FieldType.Text)
    private Set<String> ingredients;

    @Field(type = FieldType.Text)
    private Set<String> tags;

    // TODO: UI용 필드 제거하기
    /**
     * 레시피 작성자
     */
    @Field(type = FieldType.Text)
    private String authorName;

    @Field(type = FieldType.Keyword)
    private String authorProfileImgUrl;

    /**
     * 원본
     */
    @Field(type = FieldType.Text)
    private String creatorName;

    @Field(type = FieldType.Keyword)
    private String creatorProfileImgUrl;

    @Field(type = FieldType.Keyword)
    private String platform;

    @Field(type = FieldType.Keyword)
    private String sourceUrl;

    @Field(type = FieldType.Boolean)
    private Boolean isActive;

    @Field(type = FieldType.Date)
    private LocalDate createdAt;
}
