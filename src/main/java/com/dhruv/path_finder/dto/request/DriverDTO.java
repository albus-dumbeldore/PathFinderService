package com.dhruv.path_finder.dto.request;

import com.dhruv.path_finder.models.data.Location;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DriverDTO {
    @NotEmpty(message = "email ID is required")
    private String email;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Location is required")
    private Location location;
}
