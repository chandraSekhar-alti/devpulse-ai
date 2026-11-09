package com.devpulse.ai.collector.service;

import com.devpulse.ai.entity.*;
import com.devpulse.ai.repository.CollectionRunRepository;
import com.devpulse.ai.repository.CollectionSourceResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class CollectionTrackingService {

  private final CollectionRunRepository runRepository;
  private final CollectionSourceResultRepository resultRepository;

  public CollectionTrackingService(
      CollectionRunRepository runRepository, CollectionSourceResultRepository resultRepository) {
    this.runRepository = runRepository;
    this.resultRepository = resultRepository;
  }

  @Transactional
  public CollectionRun startRun(int totalSources) {
    return runRepository.save(CollectionRun.start(totalSources));
  }

  @Transactional
  public void recordSuccess(
      CollectionRun run, NewsSource source, int fetched, int saved, Instant startedAt) {

    resultRepository.save(CollectionSourceResult.success(run, source, fetched, saved, startedAt));
  }

  @Transactional
  public void recordFailure(CollectionRun run, NewsSource source, String error, Instant startedAt) {

    resultRepository.save(CollectionSourceResult.failure(run, source, error, startedAt));
  }

  @Transactional
  public void completeRun(CollectionRun run, int successful, int failed, int fetched, int saved) {

    run.complete(successful, failed, fetched, saved);
    runRepository.save(run);
  }
}
