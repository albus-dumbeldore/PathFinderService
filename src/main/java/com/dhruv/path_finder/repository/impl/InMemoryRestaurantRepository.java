package com.dhruv.path_finder.repository.impl;

import com.dhruv.path_finder.exception.NotFoundException;
import com.dhruv.path_finder.models.data.Restaurant;
import com.dhruv.path_finder.repository.repositoryInterface.RestaurantRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryRestaurantRepository implements RestaurantRepository {
    private final Map<String, Restaurant> restaurantMap;
    private final Map<String, Boolean> restaurantExistsMap;
    private final Map<String, List<String>> restaurantOrderHistoryMap;

    InMemoryRestaurantRepository() {
        restaurantMap = new HashMap<>();
        restaurantExistsMap = new HashMap<>();
        restaurantOrderHistoryMap = new HashMap<>();
    }

    @Override
    public String createRestaurant(@Nonnull Restaurant restaurant) {
        String restaurantId = UUID.randomUUID().toString();
        restaurantExistsMap.put(restaurant.getEmail(), true);
        restaurant.setRestaurantId(restaurantId);
        restaurantMap.put(restaurant.getRestaurantId(), restaurant);
        return restaurantId;
    }

    @Override
    public void saveRestaurantOrder(String restaurantId, String orderId) {
        restaurantOrderHistoryMap.computeIfAbsent(restaurantId, id -> new ArrayList<>()).add(orderId);
    }

    @Override
    public Restaurant getRestaurant(@Nonnull String id) {
        if (isRestaurantExists(id)) {
            return restaurantMap.get(id);
        }
        throw new NotFoundException("Restaurant not found with this id -> " + id);
    }

    private boolean isRestaurantExists(String id) {
        return restaurantMap.get(id) != null;
    }
}
