package com.dhruv.path_finder.models.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Data class representing a customer.
 */
@Builder
@Getter
@Setter
public class Customer {
    private String customerId;
    private String name;
    private Location location;
    private Date createdAt;
    private String email;
    private List<Order> orders;
}
