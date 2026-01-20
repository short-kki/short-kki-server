package com.example.short_kki.domain.group.entity;

import com.example.short_kki.global.entity.BaseEntity;
import com.example.short_kki.global.exception.BadRequestException;
import com.example.short_kki.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "그룹 이름은 필수이며 비어있을 수 없습니다.")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailImgUrl;

    @Enumerated(EnumType.STRING)
    private GroupType groupType = GroupType.FRIENDS;

    @NotNull(message = "그룹 코드를 생성해야 합니다.")
    @Column(nullable = false, unique = true)
    private String code;

    @Builder
    private Group(String name, String description, String thumbnailImgUrl, GroupType groupType,
            String code) {
        this.name = name;
        this.description = description;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.groupType = groupType;
        this.code = code;
    }

    public static Group create(String name, String description, String thumbnailImgUrl,
            GroupType groupType, String code) {
        return Group.builder()
                .name(name)
                .description(description)
                .thumbnailImgUrl(thumbnailImgUrl)
                .groupType(groupType)
                .code(code)
                .build();
    }

    public void updateGroupInfo(String name, String description, String thumbnailImgUrl,
            GroupType groupType) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException(ErrorCode.GROUP_NAME_EMPTY);
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