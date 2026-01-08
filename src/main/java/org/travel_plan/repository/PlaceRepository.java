package org.travel_plan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.travel_plan.model.Place;
import org.travel_plan.projection.PlaceCategory;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

    List<Place> findByCityIgnoreCase(String city);

    List<Place> findByCategory(PlaceCategory category);

    List<Place> findByCountryIgnoreCase(String country);
}
