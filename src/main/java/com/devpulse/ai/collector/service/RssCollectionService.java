package com.devpulse.ai.collector.service;

import com.devpulse.ai.collector.client.RssFeedClient;
import com.devpulse.ai.collector.model.RssFeedItem;
import com.devpulse.ai.collector.parser.RssFeedParser;
import com.devpulse.ai.entity.Article;
import com.devpulse.ai.entity.CollectionRun;
import com.devpulse.ai.entity.NewsSource;
import com.devpulse.ai.repository.NewsSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;

@Service
public class RssCollectionService {

  private static final Logger log = LoggerFactory.getLogger(RssCollectionService.class);

  private final NewsSourceRepository sourceRepository;
  private final RssFeedClient feedClient;
  private final RssFeedParser feedParser;
  private final ArticlePersistenceService persistenceService;
  private final CollectionTrackingService trackingService;

  public RssCollectionService(
      NewsSourceRepository sourceRepository,
      RssFeedClient feedClient,
      RssFeedParser feedParser,
      ArticlePersistenceService persistenceService,
      CollectionTrackingService trackingService) {

    this.sourceRepository = sourceRepository;
    this.feedClient = feedClient;
    this.feedParser = feedParser;
    this.persistenceService = persistenceService;
    this.trackingService = trackingService;
  }

  public void collectFromAllActiveSources() {

    List<NewsSource> sources = sourceRepository.findAllByActiveTrue();

    CollectionRun run = trackingService.startRun(sources.size());

    int successful = 0;
    int failed = 0;
    int totalFetched = 0;
    int totalSaved = 0;

    log.info("Collection run started. Run ID: {}", run.getId());

    for (NewsSource source : sources) {

      Instant sourceStartedAt = Instant.now();

      try {
        byte[] content = feedClient.fetch(source.getFeedUrl());

        List<RssFeedItem> items = feedParser.parse(new ByteArrayInputStream(content));

        int saved = 0;

        for (RssFeedItem item : items) {

          if (item.url() == null
              || item.url().isBlank()
              || item.title() == null
              || item.title().isBlank()) {
            continue;
          }

          Article article = mapToArticle(source, item);

          if (persistenceService.saveIfNew(article)) {
            saved++;
          }
        }

        trackingService.recordSuccess(run, source, items.size(), saved, sourceStartedAt);

        successful++;
        totalFetched += items.size();
        totalSaved += saved;

        log.info(
            "Source {} completed. Fetched: {}, saved: {}", source.getName(), items.size(), saved);

      } catch (Exception exception) {

        failed++;

        trackingService.recordFailure(run, source, exception.getMessage(), sourceStartedAt);

        log.error("Collection failed for source: {}", source.getName(), exception);
      }
    }

    trackingService.completeRun(run, successful, failed, totalFetched, totalSaved);

    log.info(
        "Collection run {} completed. Status: {}, fetched: {}, saved: {}",
        run.getId(),
        run.getStatus(),
        totalFetched,
        totalSaved);
  }

  private Article mapToArticle(NewsSource source, RssFeedItem item) {

    return Article.builder()
        .newsSource(source)
        .externalId(item.externalId())
        .title(item.title())
        .url(item.url())
        .canonicalUrl(item.url().trim())
        .author(item.author())
        .publishedAt(item.publishedAt())
        .contentText(item.content())
        .build();
  }
}
