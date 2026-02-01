package com.shortkki.api.group.repository;

import com.shortkki.api.group.entity.InviteLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteLinkRepository extends JpaRepository<InviteLink, Long>, InviteLinkRepositoryCustom {

}
