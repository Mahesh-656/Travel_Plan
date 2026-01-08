package org.travel_plan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.travel_plan.projection.PlaceCategory;
import org.travel_plan.util.BaseEntity;

@Entity
@Data
public class Place extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private PlaceCategory category;

    @Column(length = 2000)
    private String description;

    private String bestSeason;
    private double averageCost;
}

