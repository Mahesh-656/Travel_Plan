package org.travel_plan.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.travel_plan.dto.CreateTravelPlanRequest;
import org.travel_plan.model.ExternalBookingReference;
import org.travel_plan.model.TravelPlan;
import org.travel_plan.model.User;
import org.travel_plan.repository.UserRepository;
import org.travel_plan.service.TravelPlanService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/travel-plans")
@AllArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TravelPlan> createTravelPlan(@RequestBody CreateTravelPlanRequest request){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName())
                        .orElseThrow(()->new RuntimeException("user invalid"));

        TravelPlan plan=travelPlanService.createTravelPlan(
                user.getId(),
                request.getDestinationId(),
                request.getSourceLocation(),
                request.getStartDate(),
                request.getEndDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(plan);
    }
    @GetMapping
    public ResponseEntity<List<TravelPlan>> getMyTravelPlans() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        List<TravelPlan> plans = travelPlanService.getTravelPlansForUser(email);

        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlan> getTravelPlanById(@PathVariable UUID id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TravelPlan plan = travelPlanService.getTravelPlanById(id, email);
          if(plan!=null){
              return ResponseEntity.ok(plan);
          }
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TravelPlan> updateStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> request
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TravelPlan updated = travelPlanService.updateStatus(
                id,
                request.get("status"),
                email
        );

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{planId}/booking")
    public ExternalBookingReference getBooking(
            @PathVariable UUID planId,
            @AuthenticationPrincipal UserDetails user
    ) {
        return travelPlanService.getBookingDetails(planId, user.getUsername());
    }


}
