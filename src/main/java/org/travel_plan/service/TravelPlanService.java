package org.travel_plan.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.travel_plan.dto.FlightBookingRequest;
import org.travel_plan.dto.PassengerRequest;
import org.travel_plan.micro.FlightBookingClient;
import org.travel_plan.model.ExternalBookingReference;
import org.travel_plan.model.Place;
import org.travel_plan.model.TravelPlan;
import org.travel_plan.model.User;
import org.travel_plan.projection.BookingProvider;
import org.travel_plan.projection.ExternalBookingStatus;
import org.travel_plan.projection.TravelPlanStatus;
import org.travel_plan.repository.ExternalBookingReferenceRepository;
import org.travel_plan.repository.PlaceRepository;
import org.travel_plan.repository.TravelPlanRepository;
import org.travel_plan.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final FlightBookingClient flightBookingClient;
    private final ExternalBookingReferenceRepository externalBookingReferenceRepository;


    public TravelPlan createTravelPlan(UUID userId, UUID destinationId, String sourceLocation, LocalDate startDate,LocalDate endDate){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new IllegalStateException("User not found"));
        Place destination=placeRepository.findById(destinationId)
                .orElseThrow(()->new IllegalStateException("Destination not found"));
        TravelPlan plan = new TravelPlan();
        plan.setUser(user);
        plan.setDestination(destination);
        plan.setSourceLocation(sourceLocation);
        plan.setStartDate(startDate);
        plan.setEndDate(endDate);
        plan.setStatus(TravelPlanStatus.DRAFT);

        return travelPlanRepository.save(plan);
    }

    public List<TravelPlan> getTravelPlansForUser(String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));

        return travelPlanRepository.findByUserId(user.getId());

    }
    public TravelPlan
    getTravelPlanById(UUID planId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        TravelPlan plan = travelPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("TravelPlan not found"));
        if (!plan.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Access denied");
        }

        return plan;
    }

    public TravelPlan updateStatus(UUID planId, String status, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        TravelPlan plan = travelPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("TravelPlan not found"));

        if (!plan.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Access denied");
        }

        TravelPlanStatus newStatus = TravelPlanStatus.valueOf(status);

        // ✅ IDPOTENCY: already confirmed
        if (plan.getStatus() == TravelPlanStatus.CONFIRMED
                && newStatus == TravelPlanStatus.CONFIRMED) {
            return plan;
        }

        if (plan.getStatus() == TravelPlanStatus.CONFIRMED) {
            throw new IllegalStateException("Confirmed plans cannot be modified");
        }

        if (newStatus == TravelPlanStatus.CANCELLED) {
            plan.setStatus(TravelPlanStatus.CANCELLED);
            return travelPlanRepository.save(plan);
        }

        if (newStatus == TravelPlanStatus.CONFIRMED) {
            return confirmTravelPlan(plan);
        }

        throw new IllegalArgumentException("Invalid status transition");
    }

    private TravelPlan confirmTravelPlan(TravelPlan plan) {

        try {
            plan.setStatus(TravelPlanStatus.BOOKING_IN_PROGRESS);
            travelPlanRepository.save(plan);

            Integer externalBookingId =
                    flightBookingClient.createFlightBooking(buildFlightRequest(plan));

            if (externalBookingId == null) {
                throw new IllegalStateException("Flight booking did not return booking id");
            }

            externalBookingReferenceRepository.save(
                    createExternalBooking(plan, externalBookingId)
            );

            plan.setStatus(TravelPlanStatus.CONFIRMED);
            return travelPlanRepository.save(plan);

        } catch (Exception ex) {
            plan.setStatus(TravelPlanStatus.CANCELLED);
            travelPlanRepository.save(plan);
            throw new IllegalStateException("Flight booking failed", ex);
        }
    }



    private FlightBookingRequest buildFlightRequest(TravelPlan plan) {

        PassengerRequest passenger = new PassengerRequest(
                "TEMP",
                25,
                "MALE",
                "9" + (long)(Math.random() * 1_000_000_000L)
        );

        return new FlightBookingRequest(
                1, // flight id
                List.of(passenger)
        );
    }




    private ExternalBookingReference createExternalBooking(
            TravelPlan plan,
            Integer bookingId
    ) {
        ExternalBookingReference ref = new ExternalBookingReference();
        ref.setTravelPlan(plan);
        ref.setProvider(BookingProvider.FLIGHT);
        ref.setExternalBookingId(bookingId);
        ref.setStatus(ExternalBookingStatus.CONFIRMED);
        return ref;
    }


    public ExternalBookingReference getBookingDetails(UUID planId, String email) {

        TravelPlan plan = getTravelPlanById(planId, email);

        return externalBookingReferenceRepository
                .findByTravelPlanId(plan.getId())
                .orElseThrow(() -> new IllegalStateException("No booking found"));
    }

}
