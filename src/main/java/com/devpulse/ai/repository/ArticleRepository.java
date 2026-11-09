package com.devpulse.ai.repository;

import com.devpulse.ai.entity.Article;
import com.devpulse.ai.enums.ProcessingStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository
    extends JpaRepository<Article, UUID>, JpaSpecificationExecutor<Article> {

  Optional<Article> findByCanonicalUrl(String canonicalUrl);

  boolean existsByCanonicalUrl(String canonicalUrl);

  Page<Article> findAllByProcessingStatus(ProcessingStatus status, Pageable pageable);
//
//  @Query(
//      """
//            SELECT a
//            FROM Article a
//            WHERE (:status IS NULL OR a.processingStatus = :status)
//              AND (:category IS NULL OR
//                   LOWER(a.category) = LOWER(:category))
//              AND (:search IS NULL OR
//                   LOWER(a.title) LIKE LOWER(CONCAT('%', :search, '%')))
//            """)
//  Page<Article> searchArticles(
//      @Param("status") ProcessingStatus status,
//      @Param("category") String category,
//      @Param("search") String search,
//      Pageable pageable);
}
