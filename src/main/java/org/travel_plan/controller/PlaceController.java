package org.travel_plan.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travel_plan.model.Place;
import org.travel_plan.repository.PlaceRepository;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@AllArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;

    @PostMapping
    public ResponseEntity<Place> createPlace(@RequestBody Place place) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(placeRepository.save(place));
    }

    @GetMapping
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
}

