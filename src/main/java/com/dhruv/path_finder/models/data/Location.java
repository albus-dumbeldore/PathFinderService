package com.dhruv.path_finder.models.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data class representing a geographical location.
 */
@Builder
@Getter
@Setter
public class Location {
    private Double latitude;
    private Double longitude;
}
