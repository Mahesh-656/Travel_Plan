package org.travel_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.travel_plan.model.ExternalBookingReference;
import org.travel_plan.projection.BookingProvider;
import org.travel_plan.projection.ExternalBookingStatus;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExternalBookingReferenceRepository
        extends JpaRepository<ExternalBookingReference, UUID> {

    Optional<ExternalBookingReference> findByProviderAndExternalBookingId(
            BookingProvider provider,
            Integer externalBookingId
    );

    List<ExternalBookingReference> findByStatus(ExternalBookingStatus status);

    Optional<ExternalBookingReference> findByTravelPlanId(UUID travelPlanId);

}
