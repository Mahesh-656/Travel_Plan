package org.travel_plan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

import lombok.Data;
import org.travel_plan.projection.NotificationType;
import org.travel_plan.util.BaseEntity;

@Entity
@Data
public class Notification extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(length = 500)
    private String message;

    private boolean sent;
    private LocalDateTime sentAt;

    // getters & setters
}

