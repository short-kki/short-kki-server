package com.example.short_kki.domain.group.repository;

import com.example.short_kki.domain.group.entity.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long>, MemberGroupRepositoryCustom {

}
