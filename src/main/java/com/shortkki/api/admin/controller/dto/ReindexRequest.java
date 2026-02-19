package com.shortkki.api.admin.controller.dto;

import java.time.LocalDate;

public record ReindexRequest(
        boolean resetIndex,
        Long fromId,
        LocalDate fromCreatedAt
) {
}
