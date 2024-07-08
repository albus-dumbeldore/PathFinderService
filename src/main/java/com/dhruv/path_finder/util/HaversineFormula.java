package com.dhruv.path_finder.util;

import com.dhruv.path_finder.models.data.Location;
import lombok.experimental.UtilityClass;

/**
 * Utility class for calculating distances between two geographical locations
 * using the Haversine formula.
 */
@UtilityClass
public class HaversineFormula {
    private static final double R = 6372.8;

    /**
     * Calculates the distance between two locations on the Earth's surface
     * using the Haversine formula.
     *
     * @param loc1 the first location
     * @param loc2 the second location
     * @return the distance between the two locations in kilometers
     */
    public static Double distance(Location loc1, Location loc2) {
        double lat1 = loc1.getLatitude();
        double lon1 = loc1.getLongitude();
        double lat2 = loc2.getLatitude();
        double lon2 = loc2.getLongitude();

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
