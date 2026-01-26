package com.shortkki.api.group.repository;

import com.shortkki.api.group.entity.Group;

import java.util.Optional;

public interface GroupRepositoryCustom {

    Optional<Group> findByCode(String code);

    boolean existsByCode(String code);
}
