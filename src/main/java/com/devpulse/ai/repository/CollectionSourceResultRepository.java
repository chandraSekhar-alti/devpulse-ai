package com.devpulse.ai.repository;

import com.devpulse.ai.entity.CollectionSourceResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionSourceResultRepository
    extends JpaRepository<CollectionSourceResult, UUID> {

  List<CollectionSourceResult> findAllByCollectionRunId(UUID collectionRunId);
}
