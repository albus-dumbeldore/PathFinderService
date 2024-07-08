package com.dhruv.path_finder.controller;

import com.dhruv.path_finder.models.data.RouteOptimizationResult;
import com.dhruv.path_finder.dto.request.RouteOptimizerRequestDTO;
import com.dhruv.path_finder.dto.response.RouteOptimizerResponseDTO;
import com.dhruv.path_finder.service.RouteOptimizerService;
import com.dhruv.path_finder.util.ResponseDTOConversion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for route optimization operations.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pathfinder/v1")
@Validated
public class RouteOptimizerController {

    @Autowired
    private RouteOptimizerService routeOptimizerService;

    /**
     * Finds the optimal path with the minimum time for deliveries.
     *
     * @param routeOptimizerRequestDTO the request DTO containing the necessary details for route optimization
     * @return a ResponseEntity containing the minimal time and optimal path
     */
    @Operation(summary = "API for finding optimal path with minimum time")
    @ApiResponse(responseCode = "200", description = "Minimal time and optimal path")
    @PostMapping("/path")
    public ResponseEntity<RouteOptimizerResponseDTO> findPath(@Valid @RequestBody RouteOptimizerRequestDTO routeOptimizerRequestDTO) {
        RouteOptimizationResult response = routeOptimizerService.findPath(routeOptimizerRequestDTO);
        RouteOptimizerResponseDTO routeOptimizerResponseDTO = ResponseDTOConversion.convertToRouteOptimizerResponseDto(response);

        return ResponseEntity.ok(routeOptimizerResponseDTO);
    }
}
