package org.travel_plan.dto;

public record PassengerRequest(
        String name,
        int age,
        String gender,
        String contactNo
) {}

