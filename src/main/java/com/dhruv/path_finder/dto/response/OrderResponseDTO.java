package com.dhruv.path_finder.dto.response;

import com.dhruv.path_finder.models.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class OrderResponseDTO {
    private String orderId;
    private String foodName;
    private String restaurantId;
    private String customerId;
    private OrderStatus orderStatus;
    private Date createdAt;
}
