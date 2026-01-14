package com.example.short_kki.domain.group.repository;

import com.example.short_kki.domain.group.entity.QGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QGroup group = QGroup.group;
}
