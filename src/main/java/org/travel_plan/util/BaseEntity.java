package org.travel_plan.util;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.util.UUID;

@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(updatable = false,nullable = false)
    private UUID id;

    public UUID getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID();
    }
}
