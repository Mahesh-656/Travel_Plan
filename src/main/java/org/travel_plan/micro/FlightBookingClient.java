package org.travel_plan.micro;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.travel_plan.dto.ApiResponse;
import org.travel_plan.dto.FlightBookingResponse;



@Service
public class FlightBookingClient {

    private final RestTemplate restTemplate;

    public FlightBookingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Integer createFlightBooking(Object request) {

        ResponseEntity<FlightBookingResponse> response =
                restTemplate.postForEntity(
                        "http://localhost:8081/api/bookings",
                        request,
                        FlightBookingResponse.class
                );

        if (response.getBody() == null) {
            return null;
        }

        return response.getBody().id();
    }

}
