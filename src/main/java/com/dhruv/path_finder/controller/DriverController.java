package com.dhruv.path_finder.controller;

import com.dhruv.path_finder.dto.request.DriverDTO;
import com.dhruv.path_finder.dto.response.DriverResponseDTO;
import com.dhruv.path_finder.models.data.Driver;
import com.dhruv.path_finder.models.data.Location;
import com.dhruv.path_finder.util.ResponseDTOConversion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.dhruv.path_finder.service.DriverService;

/**
 * REST controller for managing drivers.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pathfinder/v1")
@Validated
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * Creates a new driver.
     *
     * @param driverDTO the driver DTO containing the driver details
     * @return a ResponseEntity containing the ID of the newly created driver
     */
    @Operation(summary = "API for creating driver")
    @ApiResponse(responseCode = "200", description = "Driver id")
    @PostMapping("/driver")
    public ResponseEntity<String> createDriver(@Valid @RequestBody DriverDTO driverDTO) {
        String driverId = driverService.createDriver(driverDTO);
        return ResponseEntity.ok("Driver created with the id -> " + driverId);
    }

    /**
     * Retrieves a driver by their ID.
     *
     * @param id the ID of the driver
     * @return a ResponseEntity containing the driver details
     */
    @Operation(summary = "API for fetching driver")
    @ApiResponse(responseCode = "200", description = "Driver")
    @GetMapping("/driver/{id}")
    public ResponseEntity<DriverResponseDTO> getDriver(@PathVariable String id) {
        Driver driver = driverService.getDriver(id);
        DriverResponseDTO driverResponseDTO = ResponseDTOConversion.convertToDriverResponseDto(driver);
        return ResponseEntity.ok(driverResponseDTO);
    }

    /**
     * Updates the location of a driver.
     *
     * @param id the ID of the driver
     * @param location the new location of the driver
     * @return a ResponseEntity confirming the update
     */
    @Operation(summary = "API for updating driver location")
    @ApiResponse(responseCode = "200", description = "Driver id updated")
    @PutMapping("/driver/{id}/location")
    public ResponseEntity<String> updateDriverLocation(@PathVariable String id, @RequestBody Location location) {
        driverService.updateDriverLocation(id, location);
        return ResponseEntity.ok("Location updated for this id -> " + id);
    }
}
