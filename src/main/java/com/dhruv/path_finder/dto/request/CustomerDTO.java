package com.dhruv.path_finder.dto.request;

import com.dhruv.path_finder.models.data.Location;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Location is required")
    private Location location;

    @NotEmpty(message = "email is required")
    private String email;

}
