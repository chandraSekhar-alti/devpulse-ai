package com.devpulse.ai.config;

import com.devpulse.ai.entity.NewsSource;
import com.devpulse.ai.enums.SourceType;
import com.devpulse.ai.repository.NewsSourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NewsSourceInitializer implements CommandLineRunner {

  private final NewsSourceRepository newsSourceRepository;

  public NewsSourceInitializer(NewsSourceRepository newsSourceRepository) {
    this.newsSourceRepository = newsSourceRepository;
  }

  @Override
  @Transactional
  public void run(String... args) {

    String feedUrl = "https://spring.io/blog.atom";

    if (!newsSourceRepository.existsByFeedUrl(feedUrl)) {

      NewsSource source =
          new NewsSource("Spring Blog", SourceType.RSS, "https://spring.io", feedUrl);

      newsSourceRepository.save(source);
    }
  }
}
