package com.devpulse.ai.dto.response;

import com.devpulse.ai.enums.CollectionStatus;

import java.time.Instant;
import java.util.UUID;

public record CollectionRunResponse(
    UUID id,
    CollectionStatus status,
    int totalSources,
    int successfulSources,
    int failedSources,
    int totalArticlesFetched,
    int totalArticlesSaved,
    Instant startedAt,
    Instant completedAt) {}
