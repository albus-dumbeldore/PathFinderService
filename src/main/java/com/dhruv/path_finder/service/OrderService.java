package com.dhruv.path_finder.service;

import com.dhruv.path_finder.dto.request.OrderDTO;
import com.dhruv.path_finder.models.data.Order;
import com.dhruv.path_finder.models.enums.OrderStatus;
import com.dhruv.path_finder.repository.repositoryInterface.OrderRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service class for managing order operations.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    RestaurantService restaurantService;

    /**
     * Creates a new order using the provided order DTO.
     *
     * @param orderDTO the order DTO containing the order details
     * @return the ID of the newly created order
     */
    public String createOrder(@Nonnull OrderDTO orderDTO) {
        Order order = Order.builder()
                .foodName(orderDTO.getFoodName())
                .customerId(orderDTO.getCustomerId())
                .restaurantId(orderDTO.getRestaurantId())
                .createdAt(new Date())
                .build();
        // update Customer order
        customerService.saveCustomerOrderDetails(order.getCustomerId(), order.getOrderId());
        // update restaurant db also
        restaurantService.saveRestaurantOrders(order.getRestaurantId(), order.getOrderId());
        return orderRepository.createOrder(order);
    }

    /**
     * Creates a batch order using the provided list of order IDs.
     *
     * @param orderIds the list of order IDs
     * @return the ID of the newly created batch order
     */
    public String createBatchOrder(List<String> orderIds) {
        return orderRepository.createBatchOrder(orderIds);
    }

    /**
     * Updates the status of an order.
     *
     * @param orderId the ID of the order
     * @param status  the new status of the order
     */
    public void updateOrderStatus(String orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the Order object
     */
    public Order getOrder(@Nonnull String id) {
        return orderRepository.getOrder(id);
    }

    /**
     * Retrieves the list of order IDs for a given batch ID.
     *
     * @param batchId the ID of the batch
     * @return the list of order IDs
     */
    public List<String> getOrderIdsByBatchId(@Nonnull final String batchId) {
        return orderRepository.getOrderIds(batchId);
    }

    /**
     * Retrieves all orders.
     *
     * @return the list of all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

}
