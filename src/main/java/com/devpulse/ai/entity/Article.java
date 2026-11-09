package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;
import com.devpulse.ai.enums.ProcessingStatus;

import jakarta.persistence.*;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "articles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "source_id", nullable = false)
  private NewsSource newsSource;

  @Column(name = "external_id", length = 255)
  private String externalId;

  @Column(nullable = false, length = 500)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String url;

  @Column(name = "canonical_url", nullable = false, unique = true, columnDefinition = "TEXT")
  private String canonicalUrl;

  @Column(length = 255)
  private String author;

  @Column(name = "published_at")
  private Instant publishedAt;

  @Column(name = "fetched_at", nullable = false, updatable = false)
  private Instant fetchedAt;

  @Column(name = "content_text", columnDefinition = "TEXT")
  private String contentText;

  @Column(name = "content_hash", length = 64)
  private String contentHash;

  @Column(length = 80)
  private String category;

  @Enumerated(EnumType.STRING)
  @Column(name = "processing_status", nullable = false, length = 30)
  private ProcessingStatus processingStatus = ProcessingStatus.PENDING;

  @Builder
  public Article(
      NewsSource newsSource,
      String externalId,
      String title,
      String url,
      String canonicalUrl,
      String author,
      Instant publishedAt,
      String contentText,
      String contentHash,
      String category) {

    this.newsSource = Objects.requireNonNull(newsSource);
    this.title = Objects.requireNonNull(title);
    this.url = Objects.requireNonNull(url);
    this.canonicalUrl = Objects.requireNonNull(canonicalUrl);

    if (title.isBlank() || url.isBlank() || canonicalUrl.isBlank()) {
      throw new IllegalArgumentException("Article title and URLs cannot be blank");
    }

    this.externalId = externalId;
    this.author = author;
    this.publishedAt = publishedAt;
    this.contentText = contentText;
    this.contentHash = contentHash;
    this.category = category;
  }

  @PrePersist
  protected void onCreate() {
    this.fetchedAt = Instant.now();
  }

  public void markProcessing() {
    this.processingStatus = ProcessingStatus.PROCESSING;
  }

  public void markCompleted() {
    this.processingStatus = ProcessingStatus.COMPLETED;
  }

  public void markFailed() {
    this.processingStatus = ProcessingStatus.FAILED;
  }

  public UUID getId() {
    return super.getId();
  }
}
