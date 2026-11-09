package com.devpulse.ai.collector.service;

import com.devpulse.ai.dto.response.*;
import com.devpulse.ai.entity.CollectionRun;
import com.devpulse.ai.entity.CollectionSourceResult;
import com.devpulse.ai.repository.CollectionRunRepository;
import com.devpulse.ai.repository.CollectionSourceResultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionHistoryService {

  private final CollectionRunRepository runRepository;
  private final CollectionSourceResultRepository resultRepository;

  public CollectionHistoryService(
      CollectionRunRepository runRepository, CollectionSourceResultRepository resultRepository) {

    this.runRepository = runRepository;
    this.resultRepository = resultRepository;
  }

  @Transactional(readOnly = true)
  public Page<CollectionRunResponse> getCollectionHistory(Pageable pageable) {

    return runRepository.findAllByOrderByStartedAtDesc(pageable).map(this::toRunResponse);
  }

  @Transactional(readOnly = true)
  public CollectionRunDetailResponse getCollectionRunDetails(UUID runId) {

    CollectionRun run =
        runRepository
            .findById(runId)
            .orElseThrow(
                () -> new CollectionRunNotFoundException("Collection run not found: " + runId));

    List<CollectionSourceResultResponse> results =
        resultRepository.findAllByCollectionRunId(runId).stream()
            .map(this::toSourceResultResponse)
            .toList();

    return new CollectionRunDetailResponse(toRunResponse(run), results);
  }

  private CollectionRunResponse toRunResponse(CollectionRun run) {

    return new CollectionRunResponse(
        run.getId(),
        run.getStatus(),
        run.getTotalSources(),
        run.getSuccessfulSources(),
        run.getFailedSources(),
        run.getTotalArticlesFetched(),
        run.getTotalArticlesSaved(),
        run.getStartedAt(),
        run.getCompletedAt());
  }

  private CollectionSourceResultResponse toSourceResultResponse(CollectionSourceResult result) {

    return new CollectionSourceResultResponse(
        result.getNewsSource().getId(),
        result.getNewsSource().getName(),
        result.isSuccessful(),
        result.getArticlesFetched(),
        result.getArticlesSaved(),
        result.getErrorMessage(),
        result.getStartedAt(),
        result.getCompletedAt());
  }
}
