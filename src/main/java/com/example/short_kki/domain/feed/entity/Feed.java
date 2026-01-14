package com.example.short_kki.domain.feed.entity;

import com.example.short_kki.domain.group.entity.Group;
import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.global.entity.BaseEntity;
import jakarta.persistence.*;
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

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private Feed(Group group, Member member, String content) {
        this.group = group;
        this.member = member;
        this.content = content;
    }

    public static Feed create(Group group, Member member, String content) {
        return Feed.builder()
                .group(group)
                .member(member)
                .content(content)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}