package com.devpulse.ai.repository;

import com.devpulse.ai.entity.NotificationDelivery;
import com.devpulse.ai.enums.DeliveryStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, UUID> {

  List<NotificationDelivery> findAllByDigestRunId(UUID digestRunId);

  List<NotificationDelivery> findAllByStatus(DeliveryStatus status);
}
