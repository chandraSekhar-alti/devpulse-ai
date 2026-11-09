package com.devpulse.ai.dto.response;

import com.devpulse.ai.enums.ProcessingStatus;

import java.time.Instant;
import java.util.UUID;

public record ArticleDetailResponse(
    UUID id,
    UUID sourceId,
    String sourceName,
    String externalId,
    String title,
    String url,
    String canonicalUrl,
    String author,
    String category,
    String contentText,
    String contentHash,
    ProcessingStatus processingStatus,
    Instant publishedAt,
    Instant fetchedAt) {}
