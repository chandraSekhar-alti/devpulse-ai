package com.devpulse.ai.service;

import com.devpulse.ai.dto.response.ArticleDetailResponse;
import com.devpulse.ai.dto.response.ArticleResponse;
import com.devpulse.ai.entity.Article;
import com.devpulse.ai.enums.ProcessingStatus;
import com.devpulse.ai.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleQueryService {

  private final ArticleRepository articleRepository;

  public ArticleQueryService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Transactional(readOnly = true)
  public Page<ArticleResponse> getArticles(
      ProcessingStatus status, String category, String search, Pageable pageable) {

    String normalizedCategory = normalize(category);
    String normalizedSearch = normalize(search);

    Specification<Article> specification =
        (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();

          // Filter by processing status
          if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("processingStatus"), status));
          }

          // Filter by category
          if (normalizedCategory != null) {
            predicates.add(
                criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("category")), normalizedCategory.toLowerCase()));
          }

          // Search by article title
          if (normalizedSearch != null) {
            predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + normalizedSearch.toLowerCase() + "%"));
          }

          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    return articleRepository.findAll(specification, pageable).map(this::toArticleResponse);
  }

  @Transactional(readOnly = true)
  public ArticleDetailResponse getArticleById(UUID id) {

    Article article =
        articleRepository
            .findById(id)
            .orElseThrow(() -> new ArticleNotFoundException("Article not found: " + id));

    return toArticleDetailResponse(article);
  }

  private ArticleResponse toArticleResponse(Article article) {

    return new ArticleResponse(
        article.getId(),
        article.getTitle(),
        article.getUrl(),
        article.getAuthor(),
        article.getCategory(),
        article.getProcessingStatus(),
        article.getPublishedAt(),
        article.getFetchedAt());
  }

  private ArticleDetailResponse toArticleDetailResponse(Article article) {

    return new ArticleDetailResponse(
        article.getId(),
        article.getNewsSource().getId(),
        article.getNewsSource().getName(),
        article.getExternalId(),
        article.getTitle(),
        article.getUrl(),
        article.getCanonicalUrl(),
        article.getAuthor(),
        article.getCategory(),
        article.getContentText(),
        article.getContentHash(),
        article.getProcessingStatus(),
        article.getPublishedAt(),
        article.getFetchedAt());
  }

  private String normalize(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }

    return value.trim();
  }
}
