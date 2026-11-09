package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;
import com.devpulse.ai.enums.ImportanceLevel;

import jakarta.persistence.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "ai_summaries")
public class AiSummary extends BaseEntity {

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "article_id", nullable = false, unique = true)
  private Article article;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String summary;

  @Column(name = "why_it_matters", columnDefinition = "TEXT")
  private String whyItMatters;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "technical_takeaways", columnDefinition = "jsonb")
  private List<String> technicalTakeaways;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ImportanceLevel importance;

  @Column(precision = 4, scale = 3)
  private BigDecimal relevanceScore;

  @Column(length = 100)
  private String modelName;

  @Column(length = 30)
  private String promptVersion;

  @Column(nullable = false, updatable = false)
  private Instant generatedAt;

  protected AiSummary() {
    // Required by JPA
  }

  public AiSummary(
      Article article,
      String summary,
      String whyItMatters,
      List<String> technicalTakeaways,
      ImportanceLevel importance,
      BigDecimal relevanceScore,
      String modelName,
      String promptVersion) {
    this.article = article;
    this.summary = summary;
    this.whyItMatters = whyItMatters;
    this.technicalTakeaways = technicalTakeaways;
    this.importance = importance;
    this.relevanceScore = relevanceScore;
    this.modelName = modelName;
    this.promptVersion = promptVersion;
  }

  @PrePersist
  protected void onCreate() {
    generatedAt = Instant.now();
  }

  public Article getArticle() {
    return article;
  }

  public String getSummary() {
    return summary;
  }

  public String getWhyItMatters() {
    return whyItMatters;
  }

  public List<String> getTechnicalTakeaways() {
    return technicalTakeaways;
  }

  public ImportanceLevel getImportance() {
    return importance;
  }

  public BigDecimal getRelevanceScore() {
    return relevanceScore;
  }

  public String getModelName() {
    return modelName;
  }

  public Instant getGeneratedAt() {
    return generatedAt;
  }
}
