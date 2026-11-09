package com.devpulse.ai.entity;

import com.devpulse.ai.entity.base.BaseEntity;
import com.devpulse.ai.enums.DeliveryStatus;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
    name = "notification_deliveries",
    indexes = {
      @Index(name = "idx_delivery_status", columnList = "status"),
      @Index(name = "idx_delivery_created_at", columnList = "created_at")
    })
public class NotificationDelivery extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "digest_run_id", nullable = false)
  private DigestRun digestRun;

  @Column(name = "recipient_email", nullable = false, length = 320)
  private String recipientEmail;

  @Column(nullable = false, length = 30)
  private String provider = "RESEND";

  @Column(name = "provider_message_id", length = 255)
  private String providerMessageId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private DeliveryStatus status = DeliveryStatus.PENDING;

  @Column(name = "sent_at")
  private Instant sentAt;

  @Column(name = "error_message", columnDefinition = "TEXT")
  private String errorMessage;

  protected NotificationDelivery() {
    // Required by JPA
  }

  public NotificationDelivery(DigestRun digestRun, String recipientEmail) {
    this.digestRun = digestRun;
    this.recipientEmail = recipientEmail;
  }

  public void markSent(String providerMessageId) {
    this.status = DeliveryStatus.SENT;
    this.providerMessageId = providerMessageId;
    this.sentAt = Instant.now();
    this.errorMessage = null;
  }

  public void markFailed(String errorMessage) {
    this.status = DeliveryStatus.FAILED;
    this.errorMessage = errorMessage;
  }

  public DigestRun getDigestRun() {
    return digestRun;
  }

  public String getRecipientEmail() {
    return recipientEmail;
  }

  public String getProviderMessageId() {
    return providerMessageId;
  }

  public DeliveryStatus getStatus() {
    return status;
  }

  public Instant getSentAt() {
    return sentAt;
  }
}
