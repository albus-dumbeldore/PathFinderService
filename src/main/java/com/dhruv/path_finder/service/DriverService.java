package com.dhruv.path_finder.service;

import com.dhruv.path_finder.dto.request.DriverDTO;
import com.dhruv.path_finder.models.data.Driver;
import com.dhruv.path_finder.models.data.Location;
import com.dhruv.path_finder.repository.repositoryInterface.DriverRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing driver operations.
 */
@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    /**
     * Creates a new driver using the provided driver DTO.
     *
     * @param driverDTO the driver DTO containing the driver details
     * @return the ID of the newly created driver
     */
    public String createDriver(@Nonnull DriverDTO driverDTO) {
        Driver driver = Driver.builder()
                .email(driverDTO.getEmail())
                .name(driverDTO.getName())
                .location(driverDTO.getLocation())
                .build();
        return driverRepository.createDriver(driver);
    }

    /**
     * Retrieves a driver by their ID.
     *
     * @param id the ID of the driver
     * @return the Driver object
     */
    public Driver getDriver(@Nonnull String id) {
        return driverRepository.getDriver(id);
    }

    /**
     * Retrieves all drivers.
     *
     * @return the list of all drivers
     */
    public List<Driver> getAllDriver() {
        return driverRepository.getAllDriver();
    }

    /**
     * Updates the location of a driver.
     *
     * @param id       the ID of the driver
     * @param location the new location of the driver
     */
    public void updateDriverLocation(@Nonnull String id, @Nonnull Location location) {
        driverRepository.updateDriverLocation(id, location);
    }
}
