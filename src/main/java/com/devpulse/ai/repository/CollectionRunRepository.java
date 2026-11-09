package com.devpulse.ai.repository;

import com.devpulse.ai.entity.CollectionRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CollectionRunRepository extends JpaRepository<CollectionRun, UUID> {
  Page<CollectionRun> findAllByOrderByStartedAtDesc(Pageable pageable);
}
