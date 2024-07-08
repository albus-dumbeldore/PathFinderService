package com.dhruv.path_finder.models.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Data class representing a restaurant.
 */
@Builder
@Getter
@Setter
public class Restaurant {
    private String restaurantId;
    private String email;
    private String name;
    private Location location;
    private Double prepTime;
    private Date createdAt;
    private List<Order> orders;
}
