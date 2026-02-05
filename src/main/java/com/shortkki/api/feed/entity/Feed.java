package com.shortkki.api.feed.entity;

import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed")
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 2000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedType feedType;

    // TODO : 좋아요 테이블을 만들어야 할까
    @PositiveOrZero(message = "좋아요는 0 미만의 숫자를 가질 수 없습니다.")
    private Long likes = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private FileMetadata image;

    @Builder
    private Feed(Group group, Member member, String content, FeedType feedType, FileMetadata image) {
        this.group = group;
        this.member = member;
        this.content = content;
        this.feedType = feedType;
        this.likes = 0L;
        this.image = image;
    }

    public static Feed create(Group group, Member member, String content, FeedType feedType, FileMetadata image) {
        return Feed.builder()
                .group(group)
                .member(member)
                .content(content)
                .feedType(feedType)
                .image(image)
                .build();
    }

    public String getImageUrl() {
        return image != null ? image.getUrl() : null;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void incrementLikes() {
        this.likes++;
    }

    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
}