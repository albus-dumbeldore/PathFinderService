package com.dhruv.path_finder.graph;

import com.dhruv.path_finder.models.data.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Component for checking SLA breaches and adjusting delivery routes.
 */
@Component
public class SlaCheckerAndRouteAdjuster {

    private static final double CUSTOMER_WAITING_SLA_HOURS = 1;

    /**
     * Adjusts the given route to account for SLA breaches.
     *
     * @param bestRoute the best route determined by the optimization algorithm
     * @param finalRoute the final adjusted route
     * @param breachedOrderIds the list of order IDs that have breached the SLA
     * @param graph the route optimization graph
     * @param orderList the list of orders
     */
    public void adjustRoute(List<String> bestRoute, List<String> finalRoute, List<String> breachedOrderIds, RouteOptimizationGraph graph, List<Order> orderList) {
        double currentTime = 0;
        Set<String> remainingCustomers = new HashSet<>();
        Set<String> visitedRestaurants = new HashSet<>();
        boolean breachOccurred = false;

        for (int i = 1; i < bestRoute.size(); i++) { // Start from the second position
            String node = bestRoute.get(i);
            String previousNode = finalRoute.get(finalRoute.size() - 1);
            double travelTime = graph.getAdjacencyMatrix().get(previousNode).get(node);
            currentTime += travelTime;

            // Consider preparation time if the current node is a restaurant
            if (graph.getRestaurantPrepTimes().containsKey(node)) {
                double prepTime = graph.getRestaurantPrepTimes().get(node) / 60;
                currentTime += prepTime;
                visitedRestaurants.add(node);
            }

            if (graph.getCustomerToRestaurant().containsKey(node)) {
                String customerId = node;
                Optional<Order> orderOpt = orderList.stream()
                        .filter(o -> o.getCustomerId().equals(customerId))
                        .findFirst();

                if (orderOpt.isPresent()) {
                    Order order = orderOpt.get();
                    if (currentTime > CUSTOMER_WAITING_SLA_HOURS) {
                        breachedOrderIds.add(order.getOrderId());
                        breachOccurred = true;
                        break; // Skip remaining customers as they will also breach SLA
                    }
                    remainingCustomers.add(customerId);
                }
            }
            finalRoute.add(node);
        }

        // Remove restaurants with no remaining customers if breach occurred
        if (breachOccurred) {
            Set<String> toRemoveRestaurants = new HashSet<>();
            for (String restaurant : visitedRestaurants) {
                boolean hasRemainingCustomer = finalRoute.stream()
                        .anyMatch(customer -> graph.getCustomerToRestaurant()
                                .getOrDefault(customer, "")
                                .equals(restaurant));
                if (!hasRemainingCustomer) {
                    toRemoveRestaurants.add(restaurant);
                }
            }
            finalRoute.removeAll(toRemoveRestaurants);
        }
    }
}
