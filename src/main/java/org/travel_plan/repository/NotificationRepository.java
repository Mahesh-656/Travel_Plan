package org.travel_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.travel_plan.model.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository
        extends JpaRepository<Notification, UUID> {

    List<Notification> findByUserId(UUID userId);

    List<Notification> findBySentFalse();
}
