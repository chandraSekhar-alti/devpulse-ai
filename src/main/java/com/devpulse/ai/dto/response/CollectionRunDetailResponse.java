package com.devpulse.ai.dto.response;

import java.util.List;

public record CollectionRunDetailResponse(
    CollectionRunResponse run, List<CollectionSourceResultResponse> sourceResults) {}
