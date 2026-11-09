package com.devpulse.ai.repository;

import com.devpulse.ai.entity.DigestRun;
import com.devpulse.ai.enums.DigestType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface DigestRunRepository extends JpaRepository<DigestRun, UUID> {

  boolean existsByDigestTypeAndPeriodStartAndPeriodEnd(
      DigestType digestType, Instant periodStart, Instant periodEnd);

  Optional<DigestRun> findByDigestTypeAndPeriodStartAndPeriodEnd(
      DigestType digestType, Instant periodStart, Instant periodEnd);
}
