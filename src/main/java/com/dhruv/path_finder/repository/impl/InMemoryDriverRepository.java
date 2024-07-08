package com.dhruv.path_finder.repository.impl;

import com.dhruv.path_finder.exception.NotFoundException;
import com.dhruv.path_finder.models.data.Driver;
import com.dhruv.path_finder.models.data.Location;
import com.dhruv.path_finder.repository.repositoryInterface.DriverRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryDriverRepository implements DriverRepository {

    private final Map<String, Driver> driverMap;
    private final Map<String, Boolean> driverExistsMap;


    InMemoryDriverRepository() {
        driverMap = new HashMap<>();
        driverExistsMap = new HashMap<>();
    }

    @Override
    public String createDriver(@Nonnull Driver driver) {
        String driverId = UUID.randomUUID().toString();
        driverExistsMap.put(driver.getEmail(), true);
        driver.setDriverId(driverId);
        driverMap.put(driverId, driver);
        return driverId;
    }

    public Driver getDriver(@Nonnull String id) {
        if (isDriverExists(id)) {
            return driverMap.get(id);
        }
        throw new NotFoundException("Driver not found with this id -> " + id);
    }

    @Override
    public List<Driver> getAllDriver() {
        return new ArrayList<>(driverMap.values());
    }

    public void updateDriverLocation(@Nonnull String id, @Nonnull Location location) {
       if (!isDriverExists(id)) {
           throw new NotFoundException("Driver not found with this id -> " + id);
       }
        Driver driver = driverMap.get(id);
        driver.setLocation(location);
    }

    private boolean isDriverExists(String id) {
        return driverMap.get(id) != null;
    }
}
