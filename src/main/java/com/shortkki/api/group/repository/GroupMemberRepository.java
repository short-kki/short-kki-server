package com.shortkki.api.group.repository;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long>, GroupMemberRepositoryCustom {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM GroupMember gm WHERE gm.group = :group")
    void deleteAllByGroup(@Param("group") Group group);
}
