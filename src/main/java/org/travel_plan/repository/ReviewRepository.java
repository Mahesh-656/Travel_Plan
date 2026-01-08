package org.travel_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.travel_plan.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository
        extends JpaRepository<Review, UUID> {

    List<Review> findByPlaceId(UUID placeId);

    boolean existsByUserIdAndPlaceId(UUID userId, UUID placeId);
}

