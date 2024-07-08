package com.dhruv.path_finder.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderDTO {
    @NotEmpty(message = "Food name is required")
    private String foodName;

    @NotEmpty(message = "Restaurant ID is required")
    private String restaurantId;

    @NotEmpty(message = "Customer ID is required")
    private String customerId;

}
