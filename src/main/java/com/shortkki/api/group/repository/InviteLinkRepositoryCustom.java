package com.shortkki.api.group.repository;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.entity.InviteLink;

import java.util.Optional;

public interface InviteLinkRepositoryCustom {

    Optional<InviteLink> findValidLinkByGroup(Group group);

    Optional<InviteLink> findByCode(String code);

    boolean existsByCode(String code);
}
