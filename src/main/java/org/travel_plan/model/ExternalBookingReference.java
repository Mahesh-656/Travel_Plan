package org.travel_plan.model;

import jakarta.persistence.*;
import lombok.Data;
import org.travel_plan.projection.BookingProvider;
import org.travel_plan.projection.ExternalBookingStatus;
import org.travel_plan.util.BaseEntity;

@Entity
@Data
public class ExternalBookingReference extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "travel_plan_id", unique = true, nullable = false)
    private TravelPlan travelPlan;


    @Enumerated(EnumType.STRING)
    private BookingProvider provider;

    @Column(nullable = false)
    private Integer externalBookingId; // from Flight Booking service

    @Enumerated(EnumType.STRING)
    private ExternalBookingStatus status;

}
