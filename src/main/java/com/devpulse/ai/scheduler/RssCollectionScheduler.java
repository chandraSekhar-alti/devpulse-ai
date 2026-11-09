package com.devpulse.ai.scheduler;

import com.devpulse.ai.collector.service.RssCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RssCollectionScheduler {

  private static final Logger log = LoggerFactory.getLogger(RssCollectionScheduler.class);

  private final RssCollectionService rssCollectionService;

  public RssCollectionScheduler(RssCollectionService rssCollectionService) {
    this.rssCollectionService = rssCollectionService;
  }

  @Scheduled(
      cron = "${devpulse.collection.rss.cron}",
      zone = "${devpulse.collection.rss.time-zone}")
  public void collectRssFeeds() {

    log.info("Scheduled RSS collection started.");

    try {
      rssCollectionService.collectFromAllActiveSources();

      log.info("Scheduled RSS collection completed.");

    } catch (Exception exception) {
      log.error("Scheduled RSS collection failed.", exception);
    }
  }
}
