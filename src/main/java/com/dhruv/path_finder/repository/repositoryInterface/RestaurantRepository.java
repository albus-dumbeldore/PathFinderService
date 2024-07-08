package com.dhruv.path_finder.repository.repositoryInterface;

import com.dhruv.path_finder.models.data.Restaurant;

/**
 * Repository interface for restaurant operations.
 */
public interface RestaurantRepository {
    /**
     * Creates a new restaurant.
     *
     * @param restaurant the restaurant to create
     * @return the ID of the newly created restaurant
     */
    public String createRestaurant(Restaurant restaurant);

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param id the ID of the restaurant
     * @return the Restaurant object
     */
    public Restaurant getRestaurant(String id);

    /**
     * Saves the association between a restaurant and an order.
     *
     * @param restaurantId the ID of the restaurant
     * @param orderId the ID of the order
     */
    public void saveRestaurantOrder(String restaurantId, String orderId);
}
