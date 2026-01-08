package org.travel_plan.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
public class CreateTravelPlanRequest {
    private UUID destinationId;
    private String sourceLocation;
    private LocalDate startDate;
    private LocalDate endDate;
}
