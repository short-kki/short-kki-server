package com.example.short_kki.domain.group.repository;

import com.example.short_kki.domain.group.entity.Group;

import java.util.Optional;

public interface GroupRepositoryCustom {

    Optional<Group> findByCode(String code);

    boolean existsByCode(String code);
}
