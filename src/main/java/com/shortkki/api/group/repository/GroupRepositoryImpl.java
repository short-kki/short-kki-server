package com.shortkki.api.group.repository;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.QGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QGroup group = QGroup.group;

    @Override
    public Optional<Group> findByCode(String code) {
        Group result = queryFactory
                .selectFrom(group)
                .where(group.code.eq(code))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByCode(String code) {
        Integer result = queryFactory
                .selectOne()
                .from(group)
                .where(group.code.eq(code))
                .fetchFirst();
        return result != null;
    }
}
