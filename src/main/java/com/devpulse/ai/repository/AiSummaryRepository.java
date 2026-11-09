package com.devpulse.ai.repository;

import com.devpulse.ai.entity.AiSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AiSummaryRepository extends JpaRepository<AiSummary, UUID> {

  Optional<AiSummary> findByArticleId(UUID articleId);
}
