package com.dhruv.path_finder.repository.repositoryInterface;

import com.dhruv.path_finder.models.data.Driver;
import com.dhruv.path_finder.models.data.Location;

import java.util.List;

/**
 * Repository interface for driver operations.
 */
public interface DriverRepository {

    /**
     * Creates a new driver.
     *
     * @param driver the driver to create
     * @return the ID of the newly created driver
     */
    String createDriver(Driver driver);

    /**
     * Retrieves a driver by their ID.
     *
     * @param id the ID of the driver
     * @return the Driver object
     */
    Driver getDriver(String id);

    /**
     * Updates the location of a driver.
     *
     * @param id the ID of the driver
     * @param location the new location of the driver
     */
    void updateDriverLocation(String id, Location location);

    /**
     * Retrieves all drivers.
     *
     * @return the list of all drivers
     */
    List<Driver> getAllDriver();
}
