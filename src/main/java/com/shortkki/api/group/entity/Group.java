package com.shortkki.api.group.entity;

import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_img_file_id")
    private FileMetadata thumbnailImgFile;

    @Enumerated(EnumType.STRING)
    private GroupType groupType = GroupType.FRIENDS;

    @Builder
    private Group(String name, String description, FileMetadata thumbnailImgFile, GroupType groupType) {
        this.name = name;
        this.description = description;
        this.thumbnailImgFile = thumbnailImgFile;
        this.groupType = groupType;
    }

    public static Group create(String name, String description, FileMetadata thumbnailImgFile,
            GroupType groupType) {
        return Group.builder()
                .name(name)
                .description(description)
                .thumbnailImgFile(thumbnailImgFile)
                .groupType(groupType)
                .build();
    }

    public String getThumbnailImgUrl() {
        return thumbnailImgFile != null ? thumbnailImgFile.getUrl() : null;
    }

    public void updateThumbnailImgFile(FileMetadata thumbnailImgFile) {
        this.thumbnailImgFile = thumbnailImgFile;
    }

    public void removeThumbnailImgFile() {
        this.thumbnailImgFile = null;
    }

    public void updateGroupInfo(String name, String description, GroupType groupType) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("그룹 이름은 필수입니다.");
        }
        this.name = name;
        if (description != null) {
            this.description = description;
        }
        if (groupType != null) {
            this.groupType = groupType;
        }
    }
}