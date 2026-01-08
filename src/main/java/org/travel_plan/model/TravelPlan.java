package org.travel_plan.model;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import lombok.Data;
import org.travel_plan.projection.TravelPlanStatus;
import org.travel_plan.util.BaseEntity;

@Entity
@Data
public class TravelPlan extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Place destination;

    private String sourceLocation;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TravelPlanStatus status;

}

