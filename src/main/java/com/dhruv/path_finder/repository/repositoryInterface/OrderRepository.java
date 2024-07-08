package com.dhruv.path_finder.repository.repositoryInterface;

import com.dhruv.path_finder.models.data.Order;
import com.dhruv.path_finder.models.enums.OrderStatus;

import java.util.List;

/**
 * Repository interface for order operations.
 */
public interface OrderRepository {

    /**
     * Creates a new order.
     *
     * @param order the order to create
     * @return the ID of the newly created order
     */
    String createOrder(Order order);

    /**
     * Creates a batch of orders.
     *
     * @param orderIds the list of order IDs to create
     * @return the ID of the newly created batch order
     */
    String createBatchOrder(List<String> orderIds);

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the Order object
     */
    Order getOrder(String id);

    /**
     * Retrieves all orders.
     *
     * @return the list of all orders
     */
    List<Order> getAllOrders();

    /**
     * Retrieves the list of order IDs for a given batch ID.
     *
     * @param batchId the ID of the batch
     * @return the list of order IDs
     */
    List<String> getOrderIds(String batchId);

    /**
     * Updates the status of an order.
     *
     * @param orderId the ID of the order
     * @param status the new status of the order
     */
    void updateOrderStatus(String orderId, OrderStatus status);
}
