package com.devpulse.ai.collector.model;

import java.time.Instant;

public record RssFeedItem(
    String externalId,
    String title,
    String url,
    String author,
    Instant publishedAt,
    String content) {}
