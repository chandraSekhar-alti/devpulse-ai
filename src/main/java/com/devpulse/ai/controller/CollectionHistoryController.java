package com.devpulse.ai.controller;

import com.devpulse.ai.collector.service.CollectionHistoryService;
import com.devpulse.ai.collector.service.CollectionRunNotFoundException;
import com.devpulse.ai.dto.response.CollectionRunDetailResponse;
import com.devpulse.ai.dto.response.CollectionRunResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/collection/runs")
public class CollectionHistoryController {

  private final CollectionHistoryService historyService;

  public CollectionHistoryController(CollectionHistoryService historyService) {
    this.historyService = historyService;
  }

  @GetMapping
  public ResponseEntity<Page<CollectionRunResponse>> getCollectionHistory(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

    if (page < 0 || size < 1 || size > 100) {
      return ResponseEntity.badRequest().build();
    }

    PageRequest pageable = PageRequest.of(page, size);

    return ResponseEntity.ok(historyService.getCollectionHistory(pageable));
  }

  @GetMapping("/{runId}")
  public ResponseEntity<CollectionRunDetailResponse> getCollectionRun(@PathVariable UUID runId) {

    return ResponseEntity.ok(historyService.getCollectionRunDetails(runId));
  }

  @ExceptionHandler(CollectionRunNotFoundException.class)
  public ResponseEntity<String> handleNotFound(CollectionRunNotFoundException exception) {

    return ResponseEntity.notFound().build();
  }
}
