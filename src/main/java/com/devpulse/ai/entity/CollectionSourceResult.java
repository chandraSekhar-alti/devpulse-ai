package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "collection_source_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectionSourceResult extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "collection_run_id", nullable = false)
  private CollectionRun collectionRun;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "source_id", nullable = false)
  private NewsSource newsSource;

  @Column(nullable = false)
  private boolean successful;

  @Column(name = "articles_fetched")
  private int articlesFetched;

  @Column(name = "articles_saved")
  private int articlesSaved;

  @Column(name = "error_message", columnDefinition = "TEXT")
  private String errorMessage;

  @Column(name = "started_at", nullable = false)
  private Instant startedAt;

  @Column(name = "completed_at")
  private Instant completedAt;

  public static CollectionSourceResult success(
      CollectionRun run, NewsSource source, int fetched, int saved, Instant startedAt) {

    CollectionSourceResult result = new CollectionSourceResult();
    result.collectionRun = run;
    result.newsSource = source;
    result.successful = true;
    result.articlesFetched = fetched;
    result.articlesSaved = saved;
    result.startedAt = startedAt;
    result.completedAt = Instant.now();

    return result;
  }

  public static CollectionSourceResult failure(
      CollectionRun run, NewsSource source, String errorMessage, Instant startedAt) {

    CollectionSourceResult result = new CollectionSourceResult();
    result.collectionRun = run;
    result.newsSource = source;
    result.successful = false;
    result.errorMessage = errorMessage;
    result.startedAt = startedAt;
    result.completedAt = Instant.now();

    return result;
  }
}
