package com.devpulse.ai.dto.response;

import java.time.Instant;
import java.util.UUID;

public record CollectionSourceResultResponse(
    UUID sourceId,
    String sourceName,
    boolean successful,
    int articlesFetched,
    int articlesSaved,
    String errorMessage,
    Instant startedAt,
    Instant completedAt) {}
