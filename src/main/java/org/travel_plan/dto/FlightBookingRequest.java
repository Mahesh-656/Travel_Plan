package org.travel_plan.dto;

import java.util.List;

public record FlightBookingRequest(
        Integer flightId,
        List<PassengerRequest> passengers
) {}

