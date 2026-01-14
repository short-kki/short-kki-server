package com.example.short_kki.domain.group.entity;

import com.example.short_kki.domain.feed.entity.Feed;
import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "groups")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailImgUrl;

    @Column(nullable = false, unique = true)
    private String code;

    @Builder
    private Group(String name, String description, String thumbnailImgUrl, String code) {
        this.name = name;
        this.description = description;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.code = code;
    }

    public static Group create(String name, String description, String thumbnailImgUrl,
            String code) {
        return Group.builder()
                .name(name)
                .description(description)
                .thumbnailImgUrl(thumbnailImgUrl)
                .code(code)
                .build();
    }

    public void updateGroupInfo(String name, String description, String thumbnailImgUrl) {
        this.name = name;
        this.description = description;
        this.thumbnailImgUrl = thumbnailImgUrl;
    }
}