package com.dhruv.path_finder.repository.impl;

import com.dhruv.path_finder.dto.response.RouteStepResponseDTO;
import com.dhruv.path_finder.repository.repositoryInterface.RouteOptimizerRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryRouteOptimizer implements RouteOptimizerRepository {

    private Map<String, List<RouteStepResponseDTO>> pathMap;

    InMemoryRouteOptimizer() {
        pathMap = new HashMap<>();
    }

    @Override
    public void savePath(String driverId, List<RouteStepResponseDTO> optimalRouteEntities) {
        String id = UUID.randomUUID().toString();
        pathMap.put(id, optimalRouteEntities);
    }

}
