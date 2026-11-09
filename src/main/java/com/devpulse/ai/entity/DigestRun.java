package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;
import com.devpulse.ai.enums.DigestStatus;
import com.devpulse.ai.enums.DigestType;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
    name = "digest_runs",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_digest_period",
          columnNames = {"digest_type", "period_start", "period_end"})
    })
public class DigestRun extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "digest_type", nullable = false, length = 20)
  private DigestType digestType;

  @Column(name = "period_start", nullable = false)
  private Instant periodStart;

  @Column(name = "period_end", nullable = false)
  private Instant periodEnd;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private DigestStatus status = DigestStatus.PENDING;

  @Column(length = 300)
  private String subject;

  @Column(name = "started_at")
  private Instant startedAt;

  @Column(name = "completed_at")
  private Instant completedAt;

  protected DigestRun() {
    // Required by JPA
  }

  public DigestRun(DigestType digestType, Instant periodStart, Instant periodEnd) {
    if (!periodEnd.isAfter(periodStart)) {
      throw new IllegalArgumentException("Period end must be after period start");
    }

    this.digestType = digestType;
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
  }

  public void start() {
    this.status = DigestStatus.PROCESSING;
    this.startedAt = Instant.now();
  }

  public void complete(String subject) {
    this.status = DigestStatus.COMPLETED;
    this.subject = subject;
    this.completedAt = Instant.now();
  }

  public void fail() {
    this.status = DigestStatus.FAILED;
    this.completedAt = Instant.now();
  }

  public DigestType getDigestType() {
    return digestType;
  }

  public Instant getPeriodStart() {
    return periodStart;
  }

  public Instant getPeriodEnd() {
    return periodEnd;
  }

  public DigestStatus getStatus() {
    return status;
  }

  public String getSubject() {
    return subject;
  }
}
