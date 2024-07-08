package com.dhruv.path_finder.models.data;

import com.dhruv.path_finder.dto.response.RouteStepResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * Data class representing the result of a route optimization.
 */
@Builder
@Getter
@Setter
public class RouteOptimizationResult {
    private String driverId;
    private String batchId;
    private Double time;
    private List<String> breachOrderIds;
    private List<RouteStepResponseDTO> optimalRouteEntities;
}
