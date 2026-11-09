package com.devpulse.ai.dto.response;

import com.devpulse.ai.enums.ProcessingStatus;

import java.time.Instant;
import java.util.UUID;

public record ArticleResponse(
    UUID id,
    String title,
    String url,
    String author,
    String category,
    ProcessingStatus processingStatus,
    Instant publishedAt,
    Instant fetchedAt) {}
