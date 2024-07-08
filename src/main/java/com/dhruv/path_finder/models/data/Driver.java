package com.dhruv.path_finder.models.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Data class representing a driver.
 */
@Builder
@Getter
@Setter
public class Driver {
    private String driverId;
    private String email;
    private String name;
    private Location location;
    private Date createdAt;
}
