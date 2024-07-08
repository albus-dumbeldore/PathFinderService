package com.dhruv.path_finder.util;

import com.dhruv.path_finder.models.data.RouteOptimizationResult;
import com.dhruv.path_finder.dto.response.*;
import com.dhruv.path_finder.models.data.Customer;
import com.dhruv.path_finder.models.data.Driver;
import com.dhruv.path_finder.models.data.Order;
import com.dhruv.path_finder.models.data.Restaurant;
import lombok.experimental.UtilityClass;

/**
 * Utility class for converting various data models to their corresponding response DTOs.
 */
@UtilityClass
public class ResponseDTOConversion {

    /**
     * Converts a Customer object to a CustomerResponseDTO object.
     *
     * @param customer the customer to convert
     * @return the CustomerResponseDTO object
     */
    public static CustomerResponseDTO convertToCustomerResponseDto(Customer customer) {
        return CustomerResponseDTO.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .location(customer.getLocation())
                .email(customer.getEmail())
                .build();
    }

    /**
     * Converts a Driver object to a DriverResponseDTO object.
     *
     * @param driver the driver to convert
     * @return the DriverResponseDTO object
     */
    public static DriverResponseDTO convertToDriverResponseDto(Driver driver) {
        return DriverResponseDTO.builder()
                .driverId(driver.getDriverId())
                .email(driver.getEmail())
                .location(driver.getLocation())
                .name(driver.getName())
                .build();
    }

    public static OrderResponseDTO convertToOrderResponseDto(Order order) {
        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .foodName(order.getFoodName())
                .restaurantId(order.getRestaurantId())
                .customerId(order.getCustomerId())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static RestaurantResponseDTO convertToRestaurantResponseDto(Restaurant restaurant) {
        return RestaurantResponseDTO.builder()
                .restaurantId(restaurant.getRestaurantId())
                .email(restaurant.getEmail())
                .location(restaurant.getLocation())
                .name(restaurant.getName())
                .prepTime(restaurant.getPrepTime())
                .build();
    }

    public static RouteOptimizerResponseDTO convertToRouteOptimizerResponseDto(RouteOptimizationResult response) {
        return RouteOptimizerResponseDTO.builder()
                .time(response.getTime())
                .routeEntities(response.getOptimalRouteEntities())
                .build();
    }
}
