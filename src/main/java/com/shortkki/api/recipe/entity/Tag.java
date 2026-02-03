package com.shortkki.api.recipe.entity;

import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "source_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TagSource sourceType;

    public static Tag createUserTag(String name) {
        return Tag.builder()
                .name(name)
                .sourceType(TagSource.USER)
                .build();
    }

    public static Tag createSystemTag(String name) {
        return Tag.builder()
                .name(name)
                .sourceType(TagSource.SYSTEM)
                .build();
    }
}
