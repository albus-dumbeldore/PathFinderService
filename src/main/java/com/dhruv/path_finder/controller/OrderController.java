package com.dhruv.path_finder.controller;

import com.dhruv.path_finder.dto.request.OrderDTO;
import com.dhruv.path_finder.dto.response.OrderResponseDTO;
import com.dhruv.path_finder.models.data.Order;
import com.dhruv.path_finder.models.enums.OrderStatus;
import com.dhruv.path_finder.service.OrderService;
import com.dhruv.path_finder.util.ResponseDTOConversion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing orders.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pathfinder/v1")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param orderDTO the order DTO containing the order details
     * @return a ResponseEntity containing the ID of the newly created order
     */
    @Operation(summary = "API for creating order")
    @ApiResponse(responseCode = "200", description = "Order id")
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        String orderId = orderService.createOrder(orderDTO);
        return ResponseEntity.ok("Order created with the id -> " + orderId);
    }

    /**
     * Creates a batch of new orders.
     *
     * @param orderDTOS the list of order DTOs containing the order details
     * @return a ResponseEntity containing the batch ID of the newly created orders
     */
    @Operation(summary = "API for creating order in batch")
    @ApiResponse(responseCode = "200", description = "batch id")
    @PostMapping("/orders")
    public ResponseEntity<String> createBatchOrder(@RequestBody List<OrderDTO> orderDTOS) {
        List<String> orderIds = orderDTOS.stream()
                .map(orderService::createOrder)
                .collect(Collectors.toList());
        String batchId = orderService.createBatchOrder(orderIds);
        return ResponseEntity.ok("Order created with the id -> " + batchId);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return a ResponseEntity containing the order details
     */
    @Operation(summary = "API for fetching order")
    @ApiResponse(responseCode = "200", description = "Order")
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable String id) {
        Order order = orderService.getOrder(id);
        OrderResponseDTO orderResponseDTO = ResponseDTOConversion.convertToOrderResponseDto(order);
        return ResponseEntity.ok(orderResponseDTO);
    }

    /**
     * Retrieves all orders.
     *
     * @return a ResponseEntity containing the list of all orders
     */
    @Operation(summary = "API for fetching all orders")
    @ApiResponse(responseCode = "200", description = "all orders")
    @GetMapping("/order")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrder() {
        List<Order> orderList = orderService.getAllOrders();
        List<OrderResponseDTO> orderResponseDTOList = orderList.stream()
                .map(ResponseDTOConversion::convertToOrderResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderResponseDTOList);
    }

    /**
     * Updates the status of an order.
     *
     * @param orderId the ID of the order
     * @param status the new status of the order
     * @return a ResponseEntity confirming the update
     */
    @Operation(summary = "API for updating order status")
    @ApiResponse(responseCode = "200", description = "batch id")
    @PostMapping("/order/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok("Order status updated for this id -> " + orderId);
    }
}
