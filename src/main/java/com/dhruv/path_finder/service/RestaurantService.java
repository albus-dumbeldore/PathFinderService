package com.dhruv.path_finder.service;

import com.dhruv.path_finder.dto.request.RestaurantDTO;
import com.dhruv.path_finder.models.data.Restaurant;
import com.dhruv.path_finder.repository.repositoryInterface.RestaurantRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for managing restaurant operations.
 */
@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Creates a new restaurant using the provided restaurant DTO.
     *
     * @param restaurantDTO the restaurant DTO containing the restaurant details
     * @return the ID of the newly created restaurant
     */
    public String createRestaurant(@Nonnull RestaurantDTO restaurantDTO) {
        Restaurant restaurant = Restaurant.builder()
                .email(restaurantDTO.getEmail())
                .name(restaurantDTO.getName())
                .location(restaurantDTO.getLocation())
                .prepTime(restaurantDTO.getPrepTime())
                .createdAt(new Date())
                .build();
        return restaurantRepository.createRestaurant(restaurant);
    }

    /**
     * Saves the association between a restaurant and an order.
     *
     * @param restaurantId the ID of the restaurant
     * @param orderId      the ID of the order
     */
    public void saveRestaurantOrders(String restaurantId, String orderId) {
        restaurantRepository.saveRestaurantOrder(restaurantId, orderId);
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param id the ID of the restaurant
     * @return the Restaurant object
     */
    public Restaurant getRestaurant(@Nonnull String id) {
        return restaurantRepository.getRestaurant(id);
    }

}
