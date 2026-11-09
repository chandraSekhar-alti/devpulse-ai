package com.devpulse.ai.collector.parser;

import com.devpulse.ai.collector.model.RssFeedItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class RssFeedParser {

  public List<RssFeedItem> parse(InputStream inputStream) {

    try {
      SyndFeedInput feedInput = new SyndFeedInput();
      SyndFeed feed = feedInput.build(new XmlReader(inputStream));

      return feed.getEntries().stream().map(this::toRssFeedItem).toList();

    } catch (Exception exception) {
      throw new RssFeedParsingException("Failed to parse RSS feed", exception);
    }
  }

  private RssFeedItem toRssFeedItem(SyndEntry entry) {

    String url = entry.getLink();

    String externalId = entry.getUri() != null ? entry.getUri() : url;

    Date publishedDate = entry.getPublishedDate();

    Instant publishedAt = publishedDate != null ? publishedDate.toInstant() : null;

    String content = entry.getDescription() != null ? entry.getDescription().getValue() : null;

    return new RssFeedItem(
        externalId, entry.getTitle(), url, entry.getAuthor(), publishedAt, content);
  }
}
