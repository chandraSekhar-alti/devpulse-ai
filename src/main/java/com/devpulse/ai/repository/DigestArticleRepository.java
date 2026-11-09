package com.devpulse.ai.repository;

import com.devpulse.ai.entity.DigestArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DigestArticleRepository extends JpaRepository<DigestArticle, UUID> {

  List<DigestArticle> findAllByDigestRunIdOrderByDisplayOrderAsc(UUID digestRunId);
}
