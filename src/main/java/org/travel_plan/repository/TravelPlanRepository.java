package org.travel_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.travel_plan.model.TravelPlan;
import org.travel_plan.projection.TravelPlanStatus;

import java.util.List;
import java.util.UUID;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, UUID> {

    List<TravelPlan> findByUserId(UUID userId);

    List<TravelPlan> findByStatus(TravelPlanStatus status);

    List<TravelPlan> findByUserIdAndStatus(UUID userId, TravelPlanStatus status);
}

