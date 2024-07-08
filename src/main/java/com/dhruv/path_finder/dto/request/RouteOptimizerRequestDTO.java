package com.dhruv.path_finder.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RouteOptimizerRequestDTO {

    @NotEmpty(message = "Driver ID is required")
    private String driverId;

    @NotEmpty(message = "batch Id required")
    private String batchId;
}