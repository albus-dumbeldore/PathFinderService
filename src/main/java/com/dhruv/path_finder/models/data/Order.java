package com.dhruv.path_finder.models.data;

import com.dhruv.path_finder.models.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Data class representing an order.
 */
@Builder
@Getter
@Setter
public class Order {
    private String orderId;
    private String foodName;
    private String restaurantId;
    private String customerId;
    private OrderStatus orderStatus;
    private Date createdAt;
}
