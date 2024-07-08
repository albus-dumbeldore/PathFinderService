package com.dhruv.path_finder.controller;

import com.dhruv.path_finder.dto.request.RestaurantDTO;
import com.dhruv.path_finder.dto.response.RestaurantResponseDTO;
import com.dhruv.path_finder.models.data.Restaurant;
import com.dhruv.path_finder.service.RestaurantService;
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
 * REST controller for managing restaurants.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pathfinder/v1")
@Validated
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Creates a new restaurant.
     *
     * @param restaurantDTO the restaurant DTO containing the restaurant details
     * @return a ResponseEntity containing the ID of the newly created restaurant
     */
    @Operation(summary = "API for creating restaurant")
    @ApiResponse(responseCode = "200", description = "Restaurant id")
    @PostMapping("/restaurant")
    public ResponseEntity<String> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        String restaurantId = restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.ok("Restaurant created with the id -> " + restaurantId);
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param id the ID of the restaurant
     * @return a ResponseEntity containing the restaurant details
     */
    @Operation(summary = "API for fetching restaurant")
    @ApiResponse(responseCode = "200", description = "restaurant")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurant(@PathVariable String id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        RestaurantResponseDTO restaurantResponseDTO =
                ResponseDTOConversion.convertToRestaurantResponseDto(restaurant);
        return ResponseEntity.ok(restaurantResponseDTO);
    }
}
