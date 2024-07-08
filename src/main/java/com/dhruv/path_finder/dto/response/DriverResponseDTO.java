package com.dhruv.path_finder.dto.response;

import com.dhruv.path_finder.models.data.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DriverResponseDTO {
    private String driverId;
    private String email;
    private String name;
    private Location location;
}
