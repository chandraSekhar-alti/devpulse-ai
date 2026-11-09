package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;
import com.devpulse.ai.enums.SourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "news_sources")
public class NewsSource extends BaseEntity {

  @Column(nullable = false, length = 150)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private SourceType sourceType;

  @Column(columnDefinition = "TEXT")
  private String baseUrl;

  @Column(unique = true, columnDefinition = "TEXT")
  private String feedUrl;

  @Column(nullable = false)
  private boolean active = true;

  protected NewsSource() {
    // Required by JPA
  }

  public NewsSource(String name, SourceType sourceType,
                    String baseUrl, String feedUrl) {
    this.name = name;
    this.sourceType = sourceType;
    this.baseUrl = baseUrl;
    this.feedUrl = feedUrl;
  }

  public String getName() {
    return name;
  }

  public SourceType getSourceType() {
    return sourceType;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public String getFeedUrl() {
    return feedUrl;
  }

  public boolean isActive() {
    return active;
  }

  public void deactivate() {
    this.active = false;
  }

  public void activate() {
    this.active = true;
  }
}