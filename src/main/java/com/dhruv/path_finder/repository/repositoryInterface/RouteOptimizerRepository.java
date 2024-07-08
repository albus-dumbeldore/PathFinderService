package com.dhruv.path_finder.repository.repositoryInterface;

import com.dhruv.path_finder.dto.response.RouteStepResponseDTO;

import java.util.List;

/**
 * Repository interface for route optimization operations.
 */
public interface RouteOptimizerRepository {
    /**
     * Saves the optimal route for a given driver.
     *
     * @param driverId the ID of the driver
     * @param optimalRouteEntities the list of optimal route entities
     */
    public void savePath(String driverId, List<RouteStepResponseDTO> optimalRouteEntities);
}
