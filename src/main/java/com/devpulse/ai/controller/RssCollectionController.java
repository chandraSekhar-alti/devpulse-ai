package com.devpulse.ai.controller;

import com.devpulse.ai.collector.service.RssCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collection/rss")
public class RssCollectionController {

  private final RssCollectionService rssCollectionService;

  public RssCollectionController(RssCollectionService rssCollectionService) {
    this.rssCollectionService = rssCollectionService;
  }

  @PostMapping("/trigger")
  public ResponseEntity<CollectionTriggerResponse> triggerCollection() {

    rssCollectionService.collectFromAllActiveSources();

    return ResponseEntity.ok(
        new CollectionTriggerResponse("COMPLETED", "RSS collection process completed."));
  }

  public record CollectionTriggerResponse(String status, String message) {}
}
