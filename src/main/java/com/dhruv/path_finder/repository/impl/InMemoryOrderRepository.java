package com.dhruv.path_finder.repository.impl;

import com.dhruv.path_finder.exception.NotFoundException;
import com.dhruv.path_finder.models.data.Order;
import com.dhruv.path_finder.models.enums.OrderStatus;
import com.dhruv.path_finder.repository.repositoryInterface.OrderRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> orderMap;
    private final Map<String, List<String>> batchOrderMap;

    InMemoryOrderRepository() {
        orderMap = new HashMap<>();
        batchOrderMap = new HashMap<>();
    }

    @Override
    public String createOrder(@Nonnull Order order) {
        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.PENDING);
        orderMap.put(orderId, order);
        return orderId;
    }

    public String createBatchOrder(List<String> orderIds) {
        String batchId = UUID.randomUUID().toString();
        batchOrderMap.put(batchId, orderIds);
        return batchId;
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        if (isOrderExists(orderId)) {
            throw new NotFoundException("Order not found with this id -> " + orderId);
        }
        orderMap.get(orderId).setOrderStatus(status);

    }

    @Override
    public Order getOrder(@Nonnull String id) {
        if (isOrderExists(id)) {
            throw new NotFoundException("Order not found with this id -> " + id);
        }
        return orderMap.get(id);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orderMap.values()) {
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public List<String> getOrderIds(String batchId) {
        return batchOrderMap.get(batchId);
    }

    private boolean isOrderExists(String orderId) {
        return orderMap.get(orderId) == null;
    }
}
