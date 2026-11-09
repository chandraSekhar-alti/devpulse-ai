package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;
import com.devpulse.ai.enums.CollectionStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "collection_runs")
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectionRun extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private CollectionStatus status;

  @Column(name = "started_at", nullable = false)
  private Instant startedAt;

  @Column(name = "completed_at")
  private Instant completedAt;

  @Column(name = "total_sources")
  private int totalSources;

  @Column(name = "successful_sources")
  private int successfulSources;

  @Column(name = "failed_sources")
  private int failedSources;

  @Column(name = "total_articles_fetched")
  private int totalArticlesFetched;

  @Column(name = "total_articles_saved")
  private int totalArticlesSaved;

  protected CollectionRun() {}

  public static CollectionRun start(int totalSources) {
    CollectionRun run = new CollectionRun();
    run.status = CollectionStatus.RUNNING;
    run.startedAt = Instant.now();
    run.totalSources = totalSources;
    return run;
  }

  public void complete(
      int successfulSources, int failedSources, int articlesFetched, int articlesSaved) {

    this.successfulSources = successfulSources;
    this.failedSources = failedSources;
    this.totalArticlesFetched = articlesFetched;
    this.totalArticlesSaved = articlesSaved;
    this.completedAt = Instant.now();

    if (failedSources == 0) {
      this.status = CollectionStatus.COMPLETED;
    } else if (successfulSources == 0) {
      this.status = CollectionStatus.FAILED;
    } else {
      this.status = CollectionStatus.PARTIAL;
    }
  }
}
