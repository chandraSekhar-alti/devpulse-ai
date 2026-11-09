package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "digest_articles",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_digest_article",
          columnNames = {"digest_run_id", "article_id"}),
      @UniqueConstraint(
          name = "uk_digest_display_order",
          columnNames = {"digest_run_id", "display_order"})
    })
public class DigestArticle extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "digest_run_id", nullable = false)
  private DigestRun digestRun;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "article_id", nullable = false)
  private Article article;

  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;

  protected DigestArticle() {
    // Required by JPA
  }

  public DigestArticle(DigestRun digestRun, Article article, Integer displayOrder) {
    if (displayOrder == null || displayOrder < 1) {
      throw new IllegalArgumentException("Display order must be positive");
    }

    this.digestRun = digestRun;
    this.article = article;
    this.displayOrder = displayOrder;
  }

  public DigestRun getDigestRun() {
    return digestRun;
  }

  public Article getArticle() {
    return article;
  }

  public Integer getDisplayOrder() {
    return displayOrder;
  }
}
