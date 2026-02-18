package com.shortkki.api.contentFeedback.repository;

import com.shortkki.api.contentFeedback.entity.ContentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentFeedbackRepository extends JpaRepository<ContentFeedback, Long> {
}
