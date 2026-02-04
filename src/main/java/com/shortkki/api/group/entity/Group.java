package com.shortkki.api.group.entity;

import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_group")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(length = 2000)
    private String description;

    private String thumbnailImgUrl;

    @Enumerated(EnumType.STRING)
    private GroupType groupType = GroupType.FRIENDS;

    @Builder
    private Group(String name, String description, String thumbnailImgUrl, GroupType groupType) {
        this.name = name;
        this.description = description;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.groupType = groupType;
    }

    public static Group create(String name, String description, String thumbnailImgUrl,
            GroupType groupType) {
        return Group.builder()
                .name(name)
                .description(description)
                .thumbnailImgUrl(thumbnailImgUrl)
                .groupType(groupType)
                .build();
    }

    public void updateGroupInfo(String name, String description, String thumbnailImgUrl,
            GroupType groupType) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("그룹 이름은 필수입니다.");
        }
        this.name = name;
        if (description != null) {
            this.description = description;
        }
        if (thumbnailImgUrl != null) {
            this.thumbnailImgUrl = thumbnailImgUrl;
        }
        if (groupType != null) {
            this.groupType = groupType;
        }
    }
}